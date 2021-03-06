package com.greencloud.facade;

import java.util.List;

import com.greencloud.entity.PosDishcard;
import com.greencloud.entity.PosPrinter;
import com.greencloud.entity.PosPrnscope;
import com.greencloud.entity.PosPserver;
import com.greencloud.entity.PosStation;

public interface IPosPrinterFacade {
	public List<PosPrinter> getPrinterList(PosPrinter printer);

	public void savePrinter(PosPrinter printer);
	public void updatePrinter(PosPrinter printer);
	public void deletePrinter(PosPrinter printer);
	
	public void saveSortPrnscope(PosPrnscope posPrnscope,String saveType);
	
	public void updateStation(PosStation station);
	public List<PosStation> getStationList(PosStation station);
	public PosPrnscope findPosPrnscope(PosPrnscope posPrnscope);
	public List<PosDishcard> getPosDishcardList(PosDishcard posDishcard);
	public List<PosDishcard> getPosDishcardListBysql(String sql);
	 
	public void savaPosPserver(PosPserver posPserver);
	public void updatePosPserver(PosPserver posPserver);
	public void saveOrUpdatePosPserver(PosPserver posPserver);
	public List<PosPserver> getPosPserver(PosPserver posPserver);
	public void deletePosPserver(PosPserver posPserver);
} 
