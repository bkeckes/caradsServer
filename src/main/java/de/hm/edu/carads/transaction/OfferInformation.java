package de.hm.edu.carads.transaction;

import de.hm.edu.carads.models.Advertiser;
import de.hm.edu.carads.models.Campaign;
import de.hm.edu.carads.models.Car;
import de.hm.edu.carads.models.util.FellowState;

/**
 * Diese Klasse repreaesentiert die Vorlage einer Fahrerantwort im JSON Format.
 * @author BK
 *
 */
public class OfferInformation {
	private Car car;
	private Campaign campaign;
	private Advertiser advertiser;
	private FellowState state;
	
	public OfferInformation(Car car, Advertiser advertiser, Campaign campaign, FellowState state){
		this.car = car;
		this.advertiser = advertiser;
		this.campaign = campaign;
		this.state = state;
	}

	public FellowState getState() {
		return state;
	}	
}
