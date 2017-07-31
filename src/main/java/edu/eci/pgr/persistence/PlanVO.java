package edu.eci.pgr.persistence;

import java.io.Serializable;

import edu.eci.pgr.business.controller.BusinessFacadePlanVO;
import edu.eci.pgr.view.MediatorPlanVO;

public class PlanVO implements Serializable{
	private static final long serialVersionUID = 1L;

	private BusinessFacadePlanVO bfplan;
	private MediatorPlanVO mplan; 
	public PlanVO() {
		super();
	}
	public PlanVO(BusinessFacadePlanVO bfplan, MediatorPlanVO mplan) {
		super();
		this.bfplan = bfplan;
		this.mplan = mplan;
	}
	public BusinessFacadePlanVO getBfplan() {
		return bfplan;
	}
	public MediatorPlanVO getMplan() {
		return mplan;
	}
	public void setBfplan(BusinessFacadePlanVO bfplan) {
		this.bfplan = bfplan;
	}
	public void setMplan(MediatorPlanVO mplan) {
		this.mplan = mplan;
	}
	
}
