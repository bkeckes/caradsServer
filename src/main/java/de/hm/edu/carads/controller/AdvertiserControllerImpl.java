package de.hm.edu.carads.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.naming.directory.InvalidAttributesException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.NoContentException;

import com.mongodb.BasicDBObject;

import de.hm.edu.carads.controller.exceptions.AlreadyExistsException;
import de.hm.edu.carads.controller.util.EntityValidator;
import de.hm.edu.carads.db.DatabaseController;
import de.hm.edu.carads.db.ModelCollection;
import de.hm.edu.carads.models.Advertiser;
import de.hm.edu.carads.models.Campaign;
import de.hm.edu.carads.models.comm.Fellow;
import de.hm.edu.carads.models.util.DateController;
import de.hm.edu.carads.models.util.TimeFrame;

public class AdvertiserControllerImpl extends AbstractEntityControllerImpl<Advertiser> implements AdvertiserController{

	public AdvertiserControllerImpl(DatabaseController database) {
		super(ModelCollection.ADVERTISER, database);
	}
	
	@Override
	public Advertiser changeEntity(String id, Advertiser entityData) throws Exception {
		if(!EntityValidator.isEntityValid((entityData)))
			throw new InvalidAttributesException();
		
		Advertiser d = getAdvertiserByEmail(entityData.getEmail());
		if(d!=null)
			if (!d.getId().equals(id))
				throw new AlreadyExistsException();

		return super.changeEntity(id, entityData);
	}
	
	@Override
	public Advertiser addEntity(Advertiser entity) throws Exception {
		if(existAdvertiserByEmail(entity.getEmail()))
			throw new AlreadyExistsException();
		return super.addEntity(entity);
	}
	
	private boolean existAdvertiserByEmail(String email) {
		if(dbController.existEntityByKeyValue(ModelCollection.ADVERTISER, "email", email))
			return true;
		return false;
	}	
	
	private Advertiser getAdvertiserByEmail(String email) {
		return this.makeEntityFromBasicDBObject(dbController.getEntityByKeyValue(ModelCollection.ADVERTISER, "email", email));
	}

	@Override
	public Campaign addCampaign(String advertiserId, Campaign campaign) throws Exception {
		if(!EntityValidator.isEntityValid(campaign))
			throw new IllegalArgumentException();
		
		Advertiser ad = getEntity(advertiserId);
		
		campaign.setId(dbController.getNewId());
		campaign.renewMetaInformation();
		ad.addCampaign(campaign);
		ad.getMetaInformation().update();
		
		dbController.updateEntity(ModelCollection.ADVERTISER, ad.getId(), BasicDBObject.parse(gson.toJson(ad)));
		
		return campaign;
	}

	@Override
	public Collection<Campaign> getCampaigns(String advertiserId) throws Exception {
		Advertiser advertiser = getEntity(advertiserId);
		
		return advertiser.getCampaigns();
	}

	@Override
	public Campaign getCampaign(String advertiserId, String campaignId) throws Exception {
		Advertiser advertiser = getEntity(advertiserId);
		Campaign c = advertiser.getCampaign(campaignId);
		if(c==null)
			throw new NoContentException(campaignId + " not found");
		
		return c;
	}

	@Override
	public void deleteCampaign(String advertiserId, String campaignId)
			throws Exception {
		Advertiser advertiser = getEntity(advertiserId);
		if(!advertiser.removeCampaign(campaignId))
			throw new NoContentException(campaignId + " not found");
		advertiser.getMetaInformation().update();
		
		dbController.updateEntity(ModelCollection.ADVERTISER, advertiserId, BasicDBObject.parse(gson.toJson(advertiser)));
	}

	@Override
	public Campaign updateCampaign(String advertiserId, String campaignId, Campaign campaign) throws Exception {		
		if(!EntityValidator.isEntityValid(campaign))
			throw new InvalidAttributesException();
		
		Advertiser advertiser = getEntity(advertiserId);
		Campaign oldC = advertiser.getCampaign(campaignId);
		if(oldC == null)
			throw new NoContentException(campaignId + " not found");

	
		advertiser.removeCampaign(campaignId);
		//campaign.setFellows(oldC.getFellows());
		campaign.update(oldC.getMetaInformation());
		campaign.setId(campaignId);
		advertiser.addCampaign(campaign);
		
		dbController.updateEntity(ModelCollection.ADVERTISER, advertiserId, BasicDBObject.parse(gson.toJson(advertiser)));
		return campaign;
	}

	@Override
	public Campaign requestVehicleForCampaign(String advertiserId, String campaignId, String carId) throws Exception {
		Advertiser advertiser = getEntity(advertiserId);
		Campaign campaign = advertiser.getCampaign(campaignId);
		
		if(isCarOccupiedInTime(carId, campaign.getStartDate(), campaign.getEndDate()))
			throw new AlreadyExistsException();
		if(campaign.isCarAFellow(carId))
			throw new AlreadyExistsException();
		if(!campaign.addFellow(carId))
			throw new IllegalArgumentException();
		
		return this.updateCampaign(advertiserId, campaignId, campaign);
	}
	
	@Override
	public boolean isCarOccupiedInTime(String carId, String start, String end) throws Exception{
		Iterator<Campaign> it = getAllCampaignsInTime(start, end).iterator();
		while(it.hasNext()){
			Campaign campaign = it.next();
			if(campaign.isCarAFellow(carId) && campaign.hasFellowAccepted(carId))
				return true;
		}
		return false;
	}
	
	private Collection<Campaign> getAllCampaignsInTime(String start, String end){
		Collection<Campaign> inTimeCampaigns = new ArrayList<Campaign>();

		
		Iterator<Campaign> it = getAllCampaigns().iterator();
		while(it.hasNext()){
			Campaign c = it.next();
			if(DateController.areTimesOverlapping(new TimeFrame(start, end), new TimeFrame(c.getStartDate(), c.getEndDate())))
				inTimeCampaigns.add(c);
//			if(DateController.isABeforeB(c.getStartDate(), end) || DateController.isAAfterB(c.getEndDate(), start))
//				inTimeCampaigns.add(c);
		}
		return inTimeCampaigns;
	}
	
	
	private Collection<Campaign> getAllCampaigns(){
		Collection<Campaign> allCampaigns = new ArrayList<Campaign>();
		
		Iterator<Advertiser> advertisers = this.getAllEntities().iterator();
		while(advertisers.hasNext()){
			allCampaigns.addAll(advertisers.next().getCampaigns());
		}
		return allCampaigns;
	}

	@Override
	public Collection<Campaign> getCarRequestingCampaigns(String carid) {		
		Collection<Campaign> carCampaigns = new ArrayList<Campaign>();
		
		Iterator<Campaign> it = getAllCampaigns().iterator();
		while(it.hasNext()){
			Campaign c = it.next();
			Iterator<Fellow> fellowIterator = c.getFellows().iterator();
			while(fellowIterator.hasNext()){
				Fellow fellow = fellowIterator.next();
				if(fellow.getCarId().equals(carid))
					carCampaigns.add(c);
			}
		}
		return carCampaigns;
	}

	@Override
	public Advertiser getAdvertiserFromCampaign(String campaignId) throws Exception {
		Iterator<Advertiser> advertIterator = getAllEntities().iterator();
		while(advertIterator.hasNext()){
			Advertiser ad = advertIterator.next();
			if(ad.containsCampaign(campaignId))
				return ad;
		}
		throw new NotFoundException();
	}
}
