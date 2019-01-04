package com.hd.agent.agprint.service.impl;

import java.util.Map;

import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.service.IAgprintService;
import com.hd.agent.agprint.service.IPrintJobService;
import com.hd.agent.agprint.service.IPrintTempletService;

public class AgprintServiceImpl implements IAgprintService {
	private IPrintJobService printJobService;
	private IPrintTempletService printTempletService;
	
	public IPrintJobService getPrintJobService() {
		return printJobService;
	}
	public IPrintTempletService getPrintTempletService() {
		return printTempletService;
	}
	public void setPrintJobService(IPrintJobService printJobService) {
		this.printJobService = printJobService;
	}
	public void setPrintTempletService(IPrintTempletService printTempletService) {
		this.printTempletService = printTempletService;
	}

	@Override
	public Map showPrintTempletByPrintQuery(Map map) throws Exception{
		return printTempletService.showPrintTempletByPrintQuery(map);
	}

	@Override
	public boolean addPrintJob(PrintJob printJob) throws Exception{
		return printJobService.addPrintJob(printJob);
	}

	@Override
	public boolean addPrintJobCallHandle(PrintJobCallHandle printJobCallHandle) throws Exception{
		return printJobService.addPrintJobCallHandle(printJobCallHandle);
	}
	@Override
	public void setTempletCommonParameter(Map parameters) throws Exception{
		printTempletService.setTempletCommonParameter(parameters);
	}

}
