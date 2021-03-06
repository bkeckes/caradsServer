package de.hm.edu.carads.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import de.hm.edu.carads.models.util.Person;

/**
 * Diese Klasse repraesentiert einen Werbenden.
 * Es werden Methoden und Attribute von der Klasse Person geerbt.
 * 
 * @author BK
 *
 */
public class Advertiser extends Person{

	/**
	 * Die Firma als String.
	 */
	private String company;
	
	/**
	 * Das Firmenlogo.
	 */
	private Image logo;
	
	/**
	 * Eine Sammlung der Kampagnen, die zu diesem Werbenden gehoeren.
	 */
	private Collection<Campaign> campaigns;
	
	/**
	 * Der Konstruktor empfaengt die wichtigsten Attribute.
	 * @param email
	 * @param firstName
	 * @param lastName
	 */
	public Advertiser(String email, String firstName, String lastName) {
		super(email, firstName, lastName);
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Image getLogo() {
		return logo;
	}

	public void setLogo(Image logo) {
		this.logo = logo;
	}
	
	private void checkCampaigns(){
		if(this.campaigns==null)
			this.campaigns = new ArrayList<Campaign>();
	}
	/**
	 * Eine neue Kampagne zu diesem Werbenden hinzugefuegt.
	 * @param campaign
	 */
	public void addCampaign(Campaign campaign) {
		checkCampaigns();
		this.campaigns.add(campaign);
	}
	
	/**
	 * Eine Kampagne des Werdbendn wird mittels der ID zurueck gegeben.
	 * @param id
	 * @return campaign
	 */
	public Campaign getCampaign(String id){
		checkCampaigns();
		Iterator<Campaign> it = campaigns.iterator();
		
		while(it.hasNext()){
			Campaign c = it.next();
			if(c.getId().equals(id))
				return c;
		}
		return null;
	}
	
	/**
	 * Wahrheitswert ueber die Existenz einer bestimmten Kampagne von dem Werbenden.
	 * @param id
	 * @return existence
	 */
	public boolean containsCampaign(String id){
		if(getCampaign(id)!=null)
			return true;
		return false;
	}
	
	/**
	 * Entfernt eine aus dem Werbenden. 
	 * @param id
	 * @return true if element was removed
	 */
	public boolean removeCampaign(Campaign campaign){
		checkCampaigns();
		return campaigns.remove(campaign);
	}
	
	/**
	 * Entfernt alle Kampagnen des Werbenden. Diese Methode
	 * wird in der Regel verwendet wenn die Anzeige der Kampagnen fuer den Benutzer
	 * nicht notwendig ist. 
	 */
	public void removeAllCampaigns(){
		this.campaigns = null;
	}
	
	/**
	 * Gibt alle Kampagnen eines Werbenden zurueck.
	 * @return campaigns
	 */
	public Collection<Campaign> getCampaigns(){ 
		checkCampaigns();
		return this.campaigns;
	}
}
