package com.greencloud.service.impl;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aua.dao.IBaseDao;
import com.aua.service.impl.BaseServiceImpl;
import com.aua.util.Container;
import com.aua.util.StringHelper;
import com.greencloud.constant.BizExceptionConstant;
import com.greencloud.dao.ICodeBaseDao;
import com.greencloud.dao.ICodeTransactionDao;
import com.greencloud.dao.IPosAccessDao;
import com.greencloud.dao.IPosAccntSyncDao;
import com.greencloud.dao.IPosAccountDao;
import com.greencloud.dao.IPosCloseDao;
import com.greencloud.dao.IPosDetailDao;
import com.greencloud.dao.IPosIpDao;
import com.greencloud.dao.IPosMasterDao;
import com.greencloud.dao.IPosMasterHistoryDao;
import com.greencloud.dao.IPosOrderDao;
import com.greencloud.dao.IPosPayDao;
import com.greencloud.dao.IPosPccodeDao;
import com.greencloud.dao.IPosPccodeShiftDao;
import com.greencloud.dao.IPosPccodeTableDao;
import com.greencloud.dao.IPosPluAllDao;
import com.greencloud.dao.IPosPluStdDao;
import com.greencloud.dao.IPosResDao;
import com.greencloud.dao.IPosSortAllDao;
import com.greencloud.dto.POSInterfaceScanDto;
import com.greencloud.dto.PosBillDto;
import com.greencloud.dto.PosMasterDto;
import com.greencloud.dto.PosMemberPointDto;
import com.greencloud.dto.PosPayDto;
import com.greencloud.dto.PosUserDto;
import com.greencloud.entity.CodeBase;
import com.greencloud.entity.CodeTransaction;
import com.greencloud.entity.PosAccess;
import com.greencloud.entity.PosAccntSync;
import com.greencloud.entity.PosAccount;
import com.greencloud.entity.PosClose;
import com.greencloud.entity.PosDetail;
import com.greencloud.entity.PosHotelTransfer;
import com.greencloud.entity.PosIp;
import com.greencloud.entity.PosMaster;
import com.greencloud.entity.PosMasterHistory;
import com.greencloud.entity.PosOrder;
import com.greencloud.entity.PosPay;
import com.greencloud.entity.PosPccode;
import com.greencloud.entity.PosPccodeShift;
import com.greencloud.entity.PosPccodeTable;
import com.greencloud.entity.PosPluAll;
import com.greencloud.entity.PosPluStd;
import com.greencloud.entity.SysOption;
import com.greencloud.exception.BizException;
import com.greencloud.facade.IMinRenTicketV2Facade;
import com.greencloud.facade.IPmsPosFacadeService;
import com.greencloud.hessian.HessianProxyFactory;
import com.greencloud.hessian.HessianProxyFactoryBean;
import com.greencloud.service.IKaiYuanInterfaceService;
import com.greencloud.service.IPosMasterService;
import com.greencloud.service.IPosOrderService;
import com.greencloud.service.ISysOptionService;
import com.greencloud.util.StringUtil;
import com.greencloud.util.UserManager;
public class PosOrderServiceImpl extends BaseServiceImpl implements IPosOrderService
{
   //dao 
   private IPosOrderDao posOrderDao;
   private IPosMasterDao posMasterDao;
   private IPosSortAllDao posSortAllDao;
   private IPosPluAllDao posPluAllDao;
   private IPosDetailDao   posDetailDao;  
   private ICodeTransactionDao codeTransactionDao;
   private IPosAccountDao  posAccountDao; 
   private IPosResDao posResDao;
   private IPosCloseDao posCloseDao;
   private IPosPluStdDao posPluStdDao;
   private IPosAccessDao posAccessDao;
   private IPosIpDao  posIpDao;
   private IPosPccodeDao posPccodeDao;
   private IPosPccodeTableDao posPccodeTableDao;
   private IPosPccodeShiftDao posPccodeShiftDao;
   private ICodeBaseDao  codeBaseDao;
   private IPosMasterHistoryDao posMasterHistoryDao;
   private IPosMasterService posMasterService ;
   private ISysOptionService sysOptionService;
   private IPosPayDao posPayDao;
   private IPosAccntSyncDao posAccntSyncDao;
   private IKaiYuanInterfaceService kaiYuanInterfaceService;
   

   public void setPosAccntSyncDao(IPosAccntSyncDao posAccntSyncDao) {
   	this.posAccntSyncDao = posAccntSyncDao;
   }
   
  public void setPosPayDao(IPosPayDao posPayDao) {
	this.posPayDao = posPayDao;
}
public void setSysOptionService(ISysOptionService sysOptionService) {
	this.sysOptionService = sysOptionService;
}

private IMinRenTicketV2Facade minRenTicketV2FacadeService;
   
   public void setMinRenTicketV2FacadeService(
		IMinRenTicketV2Facade minRenTicketV2FacadeService)
{
	this.minRenTicketV2FacadeService = minRenTicketV2FacadeService;
}
  public void setPosMasterService(IPosMasterService posMasterService)
{
	this.posMasterService = posMasterService;
}

public void setPosMasterHistoryDao(IPosMasterHistoryDao posMasterHistoryDao) {
	this.posMasterHistoryDao = posMasterHistoryDao;
}

public void setCodeBaseDao(ICodeBaseDao codeBaseDao) {
	this.codeBaseDao = codeBaseDao;
}

public void setPosPccodeShiftDao(IPosPccodeShiftDao posPccodeShiftDao) {
	this.posPccodeShiftDao = posPccodeShiftDao;
}

public void setPosPccodeTableDao(IPosPccodeTableDao posPccodeTableDao) {
	this.posPccodeTableDao = posPccodeTableDao;
}

public void setPosPccodeDao(IPosPccodeDao posPccodeDao) {
	this.posPccodeDao = posPccodeDao;
}

public void setPosIpDao(IPosIpDao posIpDao) {
	this.posIpDao = posIpDao;
}

public void setPosAccessDao(IPosAccessDao posAccessDao) {
	this.posAccessDao = posAccessDao;
}

public void setPosPluStdDao(IPosPluStdDao posPluStdDao) {
	this.posPluStdDao = posPluStdDao;
}

public void setPosCloseDao(IPosCloseDao posCloseDao) {
	this.posCloseDao = posCloseDao;
}

public void setPosResDao(IPosResDao posResDao) {
	this.posResDao = posResDao;
}

public void setPosAccountDao(IPosAccountDao posAccountDao) {
	this.posAccountDao = posAccountDao;
}

public void setCodeTransactionDao(ICodeTransactionDao codeTransactionDao) {
	this.codeTransactionDao = codeTransactionDao;
}

public void setPosDetailDao(IPosDetailDao posDetailDao) {
	this.posDetailDao = posDetailDao;
}

public void setPosMasterDao(IPosMasterDao posMasterDao) {
	this.posMasterDao = posMasterDao;
}

public void setPosSortAllDao(IPosSortAllDao posSortAllDao) {
	this.posSortAllDao = posSortAllDao;
}

public void setPosPluAllDao(IPosPluAllDao posPluAllDao) {
	this.posPluAllDao = posPluAllDao;
}

/**
   *save posOrder object  method
   *@param posOrder PosOrder 
   *@throws DataAccessException 
   *@author  
   *@version 1.0
   *@date 2015-04-27 09:43
   */
  public void savePosOrder(PosOrder posOrder) throws DataAccessException{
     	if(log.isDebugEnabled()){
			log.debug("start savePosOrder method");
		}
         posOrderDao.save(posOrder);
     	if(log.isDebugEnabled()){
			log.debug("end savePosOrder method");
		}
  }//end save method
  
   /**
   *update posOrder method
   *@param posOrder PosOrder
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-04-27 09:43
   */
  public void updatePosOrder(PosOrder posOrder) throws DataAccessException{ 
  	    if(log.isDebugEnabled()){
			log.debug("start updatePosOrder method");
		}
        posOrderDao.update(posOrder);
        if(log.isDebugEnabled()){
			log.debug("end updatePosOrder method");
		}
  }//end update method
  
   /**
   *save or update posOrder object method
   *@param posOrder PosOrder
   *@throws DataAccessException
   *@author  
   *@version 1.0
   *@date 2015-04-27 09:43
   */
  public void saveOrUpdatePosOrder(PosOrder posOrder) throws DataAccessException{//start saveOrUpdate method
      
        if(log.isDebugEnabled()){
			log.debug("start saveOrUpdatePosOrder method");
		}
		
        posOrderDao.saveOrUpdate(posOrder);
        
        if(log.isDebugEnabled()){
			log.debug("end saveOrUpdatePosOrder method");
		}
		
  }//end saveOrUpdate method

  /**
   *query a container obj
   *@param posOrder PosOrder query vo
   *@param firstResult
   *@param maxResults 
   *@throws DataAccessException 
   *@author weihuawon
   *@date 2015-04-27 09:43
   */
  public Container<PosOrder> findPosOrder(PosOrder posOrder,int firstResult ,int maxResults)throws DataAccessException{
        if(log.isDebugEnabled()){
			log.debug("start findPosOrder method");
		}
        Container<PosOrder> container = new Container<PosOrder>();
        List<PosOrder> list = posOrderDao.list(posOrder, firstResult , maxResults);
        container.setResults(list);
        container.setTotalRows(posOrderDao.count(posOrder));
        if(log.isDebugEnabled()){
			log.debug("end findPosOrder method");
		}
       return container;
  }//end find method

  /**
   *count obj amount
   *@param posOrder PosOrder query vo
   *@throws DataAccessException
   *@author 
   *@date 2015-04-27 09:43
   */
  public int countPosOrder(PosOrder posOrder)throws DataAccessException{
    return posOrderDao.count(posOrder);
  }//end count method
  
   /**
   *query a posOrder by id 
   *@param id 
   *@throws DataAccessException 
   *@author 
   *@date 2015-04-27 09:43
   */
  public PosOrder findPosOrderById(Long id)throws DataAccessException{
    PosOrder  posOrder = posOrderDao.load(id);
    return posOrder;
  }//end findPosOrderById method
  
    /**
   *query posOrder collection method
   *@param posOrder PosOrder send query vo 
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-04-27  09:43
   */
  public List<PosOrder> listPosOrder(PosOrder  posOrder)
		throws DataAccessException {
	  List<PosOrder> list = posOrderDao.list(posOrder);
      return list;
  }//end list method
  
   /**
   *query posOrder collection method
   *@param posOrder PosOrder send query vo 
   *@param firstResult 
   *@param maxResults 
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-04-27  09:43
   */
  public List<PosOrder> listPosOrder(PosOrder posOrder,int firstResult ,int maxResults)throws DataAccessException{
	   List<PosOrder> list = posOrderDao.list(posOrder, firstResult ,maxResults);
	   return list;
  }
  
  /**
   * Spring 
   * @param  posOrderDao 
   * @author weihuawon
   * @date 2015年M4月d27�?09:43
   */
  public void setPosOrderDao(IPosOrderDao posOrderDao){ 
     this.posOrderDao = posOrderDao;
  }//end setPosOrderDao method
  
  @Override
  protected IBaseDao getDao(){
    return this.posOrderDao;
  }//end getDao method

	@Override
	public List<PosOrder> savePosOrder(long hotelGroupId, long hotelId, PosMaster posMasterfront,String tableNo,PosPluAll posPluAll) {
		List<PosOrder> list = new ArrayList<PosOrder>();
		
		
		PosMaster posMaster = this.getPosMasterByAccnt(hotelGroupId, hotelId, posMasterfront.getAccnt());
		
		if(posMaster!=null && posPluAll != null){
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setPcid(UserManager.getWorkStationId().toString());
			posOrder.setAccnt(posMaster.getAccnt());
			posOrder.setInumber(0);
			posOrder.setTnumber(posMaster.getLastnum()+1);
			posOrder.setAnumber(0);
			posOrder.setMnumber(0);
			posOrder.setType("1");
			posOrder.setOrderno("0");
			posOrder.setCondCode("");
			posOrder.setDescript(posPluAll.getDescript());
			posOrder.setDescriptEn(posPluAll.getDescriptEn());
			posOrder.setNoteCode("");
			posOrder.setSortCode(posPluAll.getSortCode());
			posOrder.setPluCode(posPluAll.getCode());
			posOrder.setTocode(posPluAll.getTocode());
			posOrder.setUnit(posPluAll.getUnit());
			if(posPluAll.getPtNum() != null && posPluAll.getPtNum()>=1){
				posOrder.setNumber(new BigDecimal(posPluAll.getPtNum()));
				posOrder.setAmount(posPluAll.getPrice().multiply(new BigDecimal(posPluAll.getPtNum())));
			}else{
				posOrder.setNumber(BigDecimal.ONE);
				posOrder.setAmount(posPluAll.getPrice());
			}
			posOrder.setPrice(posPluAll.getPrice());
			posOrder.setSta("I");
			posOrder.setFlag(posPluAll.getFlag());
			posOrder.setFlag1("0000000000");
			posOrder.setEmpid(UserManager.getUserCode());
			posOrder.setLogdate(new Date());
			posOrder.setSaleid(posMaster.getSaleid());
			posOrder.setTableno(tableNo);
			posOrder.setSiteno("");
			posOrder.setCook("");
			posOrder.setRemark("");
			posOrder.setPrinter("");
			posOrder.setEmpid1(posMaster.getEmpid());
			posOrder.setEmpid2("");
			posOrder.setEmpid3("");
			posOrderDao.save(posOrder);
			
			posMaster.setLastnum(posMaster.getLastnum()+1);
			posMasterDao.update(posMaster);
			
			list.add(posOrder);
			
			PosAccess posAccess = new PosAccess();
			posAccess.setHotelGroupId(hotelGroupId);
			posAccess.setHotelId(hotelId);
			posAccess.setBizDate(UserManager.getBizDate());
			posAccess.setPluCode(posPluAll.getCode());
			List<PosAccess> alist = posAccessDao.listPosAccessForOrder(posAccess);
			if(alist != null && alist.size()>0){
				PosAccess posAccessGet = alist.get(0);
				if(posAccessGet != null){
					if(posAccessGet.getNumber().subtract(posAccessGet.getNumber1()).subtract(posOrder.getNumber()).compareTo(BigDecimal.ZERO)>=0){
						posAccessGet.setNumber1(posAccessGet.getNumber1().add(posOrder.getNumber()));
						posAccessDao.update(posAccessGet);
					}else{
						throw new BizException("'"+posPluAll.getDescript()+"'数量不足！",BizExceptionConstant.INVALID_PARAM);
					}
				}
			}
			
			if(posPluAll.getFlag() != null && !posPluAll.getFlag().equals("") && posPluAll.getFlag().length()>0){
				String taocaiFlag = posPluAll.getFlag().substring(0, 1);
				if(taocaiFlag != null && taocaiFlag.equals("1")){
					//套餐
					PosPluStd  posPluStd = new PosPluStd();
					posPluStd.setHotelGroupId(hotelGroupId);
					posPluStd.setHotelId(hotelId);
					posPluStd.setCode(posPluAll.getCode());
					posPluStd.setIsHalt("F");
					List<PosPluStd> subList = posPluStdDao.list(posPluStd);
					if(subList != null && subList.size()>0){
						for(Iterator<PosPluStd> i=subList.iterator();i.hasNext();){
							PosPluStd posPluStdGet = i.next();
							PosPluAll posPluAllSub = this.getPosPluAllByCode(hotelGroupId, hotelId, posPluStdGet.getPluCode());
							if(posPluAllSub != null){
								PosOrder posOrderSub = new PosOrder();
								posOrderSub.setHotelGroupId(hotelGroupId);
								posOrderSub.setHotelId(hotelId);
								posOrderSub.setPcid(UserManager.getWorkStationId().toString());
								posOrderSub.setAccnt(posMaster.getAccnt());
								posOrderSub.setInumber(posOrder.getTnumber());
								posOrderSub.setTnumber(posMaster.getLastnum()+1);
								posOrderSub.setAnumber(0);
								posOrderSub.setMnumber(0);
								posOrderSub.setType("1");
								posOrderSub.setOrderno("0");
								posOrderSub.setCondCode("");
								posOrderSub.setDescript(posPluAllSub.getDescript());
								posOrderSub.setDescriptEn(posPluAllSub.getDescriptEn());
								posOrderSub.setNoteCode("");
								posOrderSub.setSortCode(posPluAllSub.getSortCode());
								posOrderSub.setPluCode(posPluAllSub.getCode());
								posOrderSub.setTocode(posPluAllSub.getTocode());
								posOrderSub.setUnit(posPluStdGet.getUnit());
								posOrderSub.setNumber(posPluStdGet.getNumber().multiply(posOrder.getNumber()));
								posOrderSub.setPrice(posPluStdGet.getPrice0());
								posOrderSub.setAmount(BigDecimal.ZERO);
								posOrderSub.setSta("I");
								posOrderSub.setFlag(posPluStdGet.getFlag());
								posOrderSub.setFlag1("0000000000");
								posOrderSub.setEmpid(UserManager.getUserCode());
								posOrderSub.setLogdate(new Date());
								posOrderSub.setSaleid(posMaster.getSaleid());
								posOrderSub.setTableno(tableNo);
								posOrderSub.setSiteno("");
								posOrderSub.setCook("");
								posOrderSub.setRemark("");
								posOrderSub.setPrinter("");
								posOrderSub.setEmpid1(posMaster.getEmpid());
								posOrderSub.setEmpid2("");
								posOrderSub.setEmpid3("");
								posOrderDao.save(posOrderSub);
								list.add(posOrderSub);
								
								posMaster.setLastnum(posMaster.getLastnum()+1);
								posMasterDao.update(posMaster);
								
							}
						}
					}
				}
			}
			
			
		}
		return list;
	}
	
	@Override
	public List<PosOrder> savePosOrderNew(long hotelGroupId, long hotelId, PosMaster posMasterfront,String tableNo,PosPluAll posPluAll,BigDecimal dishNum) {
		List<PosOrder> list = new ArrayList<PosOrder>();
		
		
		PosMaster posMaster = this.getPosMasterByAccnt(hotelGroupId, hotelId, posMasterfront.getAccnt());
		
		if(posMaster!=null && posPluAll != null){
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setPcid(UserManager.getWorkStationId().toString());
			posOrder.setAccnt(posMaster.getAccnt());
			posOrder.setInumber(0);
			posOrder.setTnumber(posMaster.getLastnum()+1);
			posOrder.setAnumber(0);
			posOrder.setMnumber(0);
			posOrder.setType("1");
			posOrder.setOrderno("0");
			posOrder.setCondCode("");
			posOrder.setDescript(posPluAll.getDescript());
			posOrder.setDescriptEn(posPluAll.getDescriptEn());
			posOrder.setNoteCode("");
			posOrder.setSortCode(posPluAll.getSortCode());
			posOrder.setPluCode(posPluAll.getCode());
			posOrder.setTocode(posPluAll.getTocode());
			posOrder.setUnit(posPluAll.getUnit());
			if(dishNum != null && dishNum.compareTo(BigDecimal.ZERO) != 0){
				posOrder.setNumber(dishNum);
				posOrder.setAmount(posPluAll.getPrice().multiply(dishNum));
			}else{
				posOrder.setNumber(BigDecimal.ONE);
				posOrder.setAmount(posPluAll.getPrice());
			}
			posOrder.setPrice(posPluAll.getPrice());
			posOrder.setSta("I");
			posOrder.setFlag(posPluAll.getFlag());
			posOrder.setFlag1("0000000000");
			posOrder.setEmpid(UserManager.getUserCode());
			posOrder.setLogdate(new Date());
			posOrder.setSaleid(posMaster.getSaleid());
			posOrder.setTableno(tableNo);
			posOrder.setSiteno("");
			posOrder.setCook("");
			posOrder.setRemark("");
			posOrder.setPrinter("");
			posOrder.setEmpid1(posMaster.getEmpid());
			posOrder.setEmpid2("");
			posOrder.setEmpid3("");
			posOrderDao.save(posOrder);
			
			posMaster.setLastnum(posMaster.getLastnum()+1);
			posMasterDao.update(posMaster);
			
			list.add(posOrder);
			
			PosAccess posAccess = new PosAccess();
			posAccess.setHotelGroupId(hotelGroupId);
			posAccess.setHotelId(hotelId);
			posAccess.setBizDate(UserManager.getBizDate());
			posAccess.setPluCode(posPluAll.getCode());
			List<PosAccess> alist = posAccessDao.listPosAccessForOrder(posAccess);
			if(alist != null && alist.size()>0){
				PosAccess posAccessGet = alist.get(0);
				if(posAccessGet != null){
					if(posAccessGet.getNumber().subtract(posAccessGet.getNumber1()).subtract(posOrder.getNumber()).compareTo(BigDecimal.ZERO)>=0){
						posAccessGet.setNumber1(posAccessGet.getNumber1().add(posOrder.getNumber()));
						posAccessDao.update(posAccessGet);
					}else{
						throw new BizException("'"+posPluAll.getDescript()+"'数量不足！",BizExceptionConstant.INVALID_PARAM);
					}
				}
			}
			
			if(posPluAll.getFlag() != null && !posPluAll.getFlag().equals("") && posPluAll.getFlag().length()>0){
				String taocaiFlag = posPluAll.getFlag().substring(0, 1);
				if(taocaiFlag != null && taocaiFlag.equals("1")){
					//套餐
					PosPluStd  posPluStd = new PosPluStd();
					posPluStd.setHotelGroupId(hotelGroupId);
					posPluStd.setHotelId(hotelId);
					posPluStd.setCode(posPluAll.getCode());
					posPluStd.setIsHalt("F");
					List<PosPluStd> subList = posPluStdDao.list(posPluStd);
					if(subList != null && subList.size()>0){
						for(Iterator<PosPluStd> i=subList.iterator();i.hasNext();){
							PosPluStd posPluStdGet = i.next();
							PosPluAll posPluAllSub = this.getPosPluAllByCode(hotelGroupId, hotelId, posPluStdGet.getPluCode());
							if(posPluAllSub != null){
								PosOrder posOrderSub = new PosOrder();
								posOrderSub.setHotelGroupId(hotelGroupId);
								posOrderSub.setHotelId(hotelId);
								posOrderSub.setPcid(UserManager.getWorkStationId().toString());
								posOrderSub.setAccnt(posMaster.getAccnt());
								posOrderSub.setInumber(posOrder.getTnumber());
								posOrderSub.setTnumber(posMaster.getLastnum()+1);
								posOrderSub.setAnumber(0);
								posOrderSub.setMnumber(0);
								posOrderSub.setType("1");
								posOrderSub.setOrderno("0");
								posOrderSub.setCondCode("");
								posOrderSub.setDescript(posPluAllSub.getDescript());
								posOrderSub.setDescriptEn(posPluAllSub.getDescriptEn());
								posOrderSub.setNoteCode("");
								posOrderSub.setSortCode(posPluAllSub.getSortCode());
								posOrderSub.setPluCode(posPluAllSub.getCode());
								posOrderSub.setTocode(posPluAllSub.getTocode());
								posOrderSub.setUnit(posPluStdGet.getUnit());
								posOrderSub.setNumber(posPluStdGet.getNumber().multiply(posOrder.getNumber()));
								posOrderSub.setPrice(posPluStdGet.getPrice0());
								posOrderSub.setAmount(BigDecimal.ZERO);
								posOrderSub.setSta("I");
								posOrderSub.setFlag(posPluStdGet.getFlag());
								posOrderSub.setFlag1("0000000000");
								posOrderSub.setEmpid(UserManager.getUserCode());
								posOrderSub.setLogdate(new Date());
								posOrderSub.setSaleid(posMaster.getSaleid());
								posOrderSub.setTableno(tableNo);
								posOrderSub.setSiteno("");
								posOrderSub.setCook("");
								posOrderSub.setRemark("");
								posOrderSub.setPrinter("");
								posOrderSub.setEmpid1(posMaster.getEmpid());
								posOrderSub.setEmpid2("");
								posOrderSub.setEmpid3("");
								posOrderDao.save(posOrderSub);
								list.add(posOrderSub);
								
								posMaster.setLastnum(posMaster.getLastnum()+1);
								posMasterDao.update(posMaster);
								
							}
						}
					}
				}
			}
			
			
		}
		return list;
	}
	
	private PosPluAll getPosPluAllByCode(long hotelGroupId, long hotelId, String code){
		PosPluAll posPluAll = new PosPluAll();
		posPluAll.setHotelGroupId(hotelGroupId);
		posPluAll.setHotelId(hotelId);
		posPluAll.setCode(code);
		List<PosPluAll> list = posPluAllDao.list(posPluAll);
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	private PosMaster getPosMasterByAccnt(long hotelGroupId, long hotelId, String accnt){
		PosMaster posMaster = new PosMaster();
		posMaster.setHotelGroupId(hotelGroupId);
		posMaster.setHotelId(hotelId);
		posMaster.setAccnt(accnt);
		List<PosMaster> list = posMasterDao.list(posMaster);
		if(list !=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	private PosMasterHistory getPosMasterHistoryByAccnt(long hotelGroupId, long hotelId, String accnt){
		PosMasterHistory posMasterHistory = new PosMasterHistory();
		posMasterHistory.setHotelGroupId(hotelGroupId);
		posMasterHistory.setHotelId(hotelId);
		posMasterHistory.setAccnt(accnt);
		List<PosMasterHistory> list = posMasterHistoryDao.list(posMasterHistory);
		if(list !=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void deletePosOrder(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo) {
		if(posMaster!=null && tableNo != null){
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setAccnt(posMaster.getAccnt());
			posMaster.setTableno(tableNo);
			List<PosOrder> list=posOrderDao.list(posOrder);
			if(list != null && list.size()>0){
				posOrderDao.deleteAll(list);
			}
			
		}
	}

	@Override
	public String updateOrderToDetail(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo,String info) {
		HashMap<Long, Long> map = new HashMap<Long, Long>();
		String newIds="";
		
		PosMaster posMasterSet = new PosMaster();
		posMasterSet.setHotelGroupId(hotelGroupId);
		posMasterSet.setHotelId(hotelId);
		posMasterSet.setAccnt(posMaster.getAccnt());
		List<PosMaster> posMasterList = posMasterDao.list(posMasterSet);
		if(posMasterList != null && posMasterList.size() > 0 ){
			PosMaster posMasterGet = posMasterList.get(0);
			if(posMasterGet.getSta().equals("O") || posMasterGet.getSta().equals("X")){
				throw new BizException("该主单已处于结账或非有效状态，不允许下单！",BizExceptionConstant.INVALID_PARAM);
			}
		}
		
		PosOrder posOrder = new PosOrder();
		posOrder.setHotelGroupId(hotelGroupId);
		posOrder.setHotelId(hotelId);
		posOrder.setAccnt(posMaster.getAccnt());
		posOrder.setTableno(tableNo);
		//posOrder.setPcid(UserManager.getWorkStationId().toString());
		List<PosOrder> list=posOrderDao.list(posOrder);
		if(list != null && list.size()>0){
			for(Iterator<PosOrder> i=list.iterator();i.hasNext();){
				PosOrder posOrderGet = i.next();
				long orgId=posOrderGet.getId();
				
				
				PosDetail posDetail = new PosDetail();
				posDetail.setHotelGroupId(posOrderGet.getHotelGroupId());
				posDetail.setHotelId(posOrderGet.getHotelId());
				posDetail.setAccnt(posOrderGet.getAccnt());
				posDetail.setInumber(posOrderGet.getInumber());
				posDetail.setTnumber(posOrderGet.getTnumber());
				posDetail.setAnumber(posOrderGet.getAnumber());
				posDetail.setMnumber(posOrderGet.getMnumber());
				posDetail.setType(posOrderGet.getType());
				posDetail.setOrderno("1");
				posDetail.setSta("I");
				posDetail.setShift(UserManager.getCashier().toString());
				posDetail.setEmpid(UserManager.getUserCode());
				posDetail.setBizDate(UserManager.getBizDate());
				posDetail.setNoteCode(posOrderGet.getNoteCode());
				posDetail.setSortCode(posOrderGet.getSortCode());
				posDetail.setCode(posOrderGet.getPluCode());
				posDetail.setTocode(posOrderGet.getTocode());//posOrder需要加tocode字段
				posDetail.setCondCode(posOrderGet.getCondCode());
				posDetail.setCook(posOrderGet.getCook());
				posDetail.setPrintid(0);
				posDetail.setDescript(posOrderGet.getDescript());
				posDetail.setDescriptEn(posOrderGet.getDescriptEn());
				posDetail.setUnit(posOrderGet.getUnit());
				posDetail.setNumber(posOrderGet.getNumber());
				posDetail.setPrice(posOrderGet.getPrice());
				posDetail.setPinumber(0);
				posDetail.setAmount(posOrderGet.getAmount());
				posDetail.setAmount1(BigDecimal.ZERO);
				posDetail.setAmount2(BigDecimal.ZERO);
				posDetail.setAmount3(BigDecimal.ZERO);
				posDetail.setAmount4(BigDecimal.ZERO);
				posDetail.setAmount5(BigDecimal.ZERO);
				posDetail.setCost(BigDecimal.ZERO);
				posDetail.setFlag(posOrderGet.getFlag());
				posDetail.setFlag1(posOrderGet.getFlag1());
				posDetail.setReason("");
				posDetail.setDsc(BigDecimal.ZERO);
				posDetail.setSrv(BigDecimal.ZERO);
				posDetail.setSrv0(BigDecimal.ZERO);
				posDetail.setSrvDsc(BigDecimal.ZERO);
				posDetail.setTax(BigDecimal.ZERO);
				posDetail.setTax0(BigDecimal.ZERO);
				posDetail.setTaxDsc(BigDecimal.ZERO);
				posDetail.setTableno(posOrderGet.getTableno());
				posDetail.setSiteno(posOrderGet.getSiteno());
				posDetail.setInfo(info);
				posDetail.setCardno("");
				posDetail.setCardinfo("");
				posDetail.setKitchen("");
				posDetail.setPcid(UserManager.getWorkStationId().toString());
				posDetail.setEmpid(posOrderGet.getEmpid());
				posDetail.setEmpid1(posOrderGet.getEmpid1());
				posDetail.setEmpid2(posOrderGet.getEmpid2());
				posDetail.setEmpid3(posOrderGet.getEmpid3());
				posDetail.setDrawDate(null);
				
				//posDetail.setKitchen("004#004#");
				
				
				posDetailDao.save(posDetail);
				
				newIds = newIds+posDetail.getId().toString()+",";
				
			//	posDetailDao.updateKitchenInputDishcard(posDetail.getHotelGroupId().toString(), posDetail.getHotelId().toString(), posDetail.getAccnt(), posDetail.getId().toString(), "1", UserManager.getWorkStationId().toString());
				
				map.put(orgId, posDetail.getId());
				posOrderDao.delete(posOrderGet);
				
			}
		}
		
		return newIds;
	}

	@Override
	public void updateDealNum(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo, String type, long id,String dealType) {
	    String currentPluCode = "";
	    String currentPludesc = "";
	    int num = 0;
		
		if(type.equals("1")){
			//order
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setAccnt(posMaster.getAccnt());
			posOrder.setTableno(tableNo);
			posOrder.setId(id);
			List<PosOrder> list = posOrderDao.list(posOrder);
			if(list != null && list.size()>0){
				PosOrder posOrderGet = list.get(0);
				currentPluCode = posOrderGet.getPluCode();
				currentPludesc = posOrderGet.getDescript();
				
				if(dealType.equals("ADD")){
					num=1;
					posOrderGet.setNumber(posOrderGet.getNumber().add(BigDecimal.ONE));
					posOrderGet.setAmount(posOrderGet.getNumber().multiply(posOrderGet.getPrice()));
					posOrderDao.update(posOrderGet);
				}else if(dealType.equals("SUB")){
					if(posOrderGet.getNumber().compareTo(BigDecimal.ONE) == 1){
						num = -1;
						posOrderGet.setNumber(posOrderGet.getNumber().subtract(BigDecimal.ONE));
						posOrderGet.setAmount(posOrderGet.getNumber().multiply(posOrderGet.getPrice()));
						posOrderDao.update(posOrderGet);
					}
				}
			}
			
		}else if(type.equals("0")){
			//detail
			PosDetail posDetail= new PosDetail();
			posDetail.setHotelGroupId(hotelGroupId);
			posDetail.setHotelId(hotelId);
			posDetail.setAccnt(posMaster.getAccnt());
			posDetail.setTableno(tableNo);
			posDetail.setId(id);
			List<PosDetail> list1 = posDetailDao.list(posDetail);
			if(list1 != null && list1.size()>0){
				PosDetail posDetailGet = list1.get(0);
				currentPluCode = posDetailGet.getCode();
				currentPludesc = posDetailGet.getDescript();
				
				if(dealType.equals("ADD")){
					num =1 ;
					posDetailGet.setNumber(posDetailGet.getNumber().add(BigDecimal.ONE));
					posDetailGet.setAmount(posDetailGet.getNumber().multiply(posDetailGet.getPrice()));
					posDetailDao.update(posDetailGet);
				}else if(dealType.equals("SUB")){
					if(posDetailGet.getNumber().compareTo(BigDecimal.ONE) == 1){
						num=-1;
						posDetailGet.setNumber(posDetailGet.getNumber().subtract(BigDecimal.ONE));
						posDetailGet.setAmount(posDetailGet.getNumber().multiply(posDetailGet.getPrice()));
						posDetailDao.update(posDetailGet);
					}
				}
			}
		}
		
		PosAccess posAccess = new PosAccess();
		posAccess.setHotelGroupId(hotelGroupId);
		posAccess.setHotelId(hotelId);
		posAccess.setBizDate(UserManager.getBizDate());
		posAccess.setPluCode(currentPluCode);
		List<PosAccess> alist = posAccessDao.listPosAccessForOrder(posAccess);
		if(alist != null && alist.size()>0){
			PosAccess posAccessGet = alist.get(0);
			if(posAccessGet != null){
				
				if(num == 1){
					//加数量 判断估清是否够
					if(posAccessGet.getNumber().subtract(posAccessGet.getNumber1()).subtract(BigDecimal.ONE).compareTo(BigDecimal.ZERO)>=0){
						posAccessGet.setNumber1(posAccessGet.getNumber1().add(BigDecimal.ONE));
						posAccessDao.update(posAccessGet);
					}else{
						throw new BizException("'"+currentPludesc+"'数量不足！",BizExceptionConstant.INVALID_PARAM);
					}
				}else if(num == -1){
					posAccessGet.setNumber1(posAccessGet.getNumber1().subtract(BigDecimal.ONE));
					posAccessDao.update(posAccessGet);
				}
				
			}
		}
	}
	
	
	public BigDecimal getNumByCode(long hotelGroupId, long hotelId,PosMaster posMaster,String code){
		BigDecimal num = BigDecimal.ZERO;
		
		PosDetail posDetail= new PosDetail();
		posDetail.setHotelGroupId(hotelGroupId);
		posDetail.setHotelId(hotelId);
		posDetail.setAccnt(posMaster.getAccnt());
		posDetail.setCode(code);
		List<PosDetail> list1 = posDetailDao.list(posDetail);
		if(list1 != null && list1.size()>0){
			for(Iterator<PosDetail> i = list1.iterator();i.hasNext();){
				PosDetail posDetailGte= i.next();
				num = num.add(posDetailGte.getNumber());
			}
		}
		
		return num;
	}

	@Override
	public String updateCanclePlu(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo, String type, long id,String info,BigDecimal number) {
		 String ids="";
//		退菜判断主单状态
		 PosMaster posMasterSta = new PosMaster();
		 posMasterSta.setHotelGroupId(hotelGroupId);
		 posMasterSta.setHotelId(hotelId);
		 posMasterSta.setAccnt(posMaster.getAccnt());
		 List<PosMaster> posMasterList = posMasterDao.list(posMasterSta);
		 if(posMasterList != null && posMasterList.size() >0){
			if("O".equals(posMasterList.get(0).getSta()) || "X".equals(posMasterList.get(0).getSta())){
				throw new BizException("改主单已经是结账或者无效状态,不能退菜,请刷新餐问题再操作",BizExceptionConstant.INVALID_PARAM);
			}
		 }
		 		
		 String currentPluCode = "";
		 String currentPludesc = "";
		 BigDecimal num = BigDecimal.ZERO;
		if(type.equals("1")){
			//order
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setAccnt(posMaster.getAccnt());
			posOrder.setTableno(tableNo);
			posOrder.setId(id);
			List<PosOrder> list = posOrderDao.list(posOrder);
			if(list != null && list.size()>0){
				PosOrder posOrderGet = list.get(0);
				
				int tnum=posOrderGet.getTnumber();
				
				currentPluCode = posOrderGet.getPluCode();
				num = posOrderGet.getNumber();
				posOrderDao.delete(posOrderGet);
				
				//套菜删除
				if(tnum != 0){
					PosOrder posOrderSub = new PosOrder();
					posOrderSub.setHotelGroupId(hotelGroupId);
					posOrderSub.setHotelId(hotelId);
					posOrderSub.setAccnt(posMaster.getAccnt());
					posOrderSub.setTableno(tableNo);
					posOrderSub.setInumber(tnum);
					List<PosOrder> listsub = posOrderDao.list(posOrderSub);
					if(listsub != null && listsub.size()>0){
						posOrderDao.deleteAll(listsub);
					}
				}
			}
			
		}else if(type.equals("0")){
			//detail
			PosDetail posDetail= new PosDetail();
			posDetail.setHotelGroupId(hotelGroupId);
			posDetail.setHotelId(hotelId);
			posDetail.setAccnt(posMaster.getAccnt());
			posDetail.setTableno(tableNo);
			posDetail.setId(id);
			List<PosDetail> list1 = posDetailDao.list(posDetail);
			if(list1 != null && list1.size()>0){
				PosDetail posDetailGet = list1.get(0);
				
				BigDecimal orgNum = this.getNumByCode(hotelGroupId, hotelId, posMaster, posDetailGet.getCode());
				if(number.compareTo(orgNum)>0){
					throw new BizException("'"+posDetailGet.getDescript()+"'数量不足！",BizExceptionConstant.INVALID_PARAM);
				}
				num = number;
				currentPluCode = posDetailGet.getCode();
			
				
				PosDetail posDetailCopy = posDetailGet.cloneEx();
				posDetailCopy.setId(null);
				posDetailCopy.setHotelGroupId(null);
				posDetailCopy.setHotelId(null);
				posDetailCopy.setCreateDatetime(null);
				posDetailCopy.setCreateUser(null);
				posDetailCopy.setModifyUser(null);
				posDetailCopy.setModifyDatetime(null);
				posDetailCopy.setNumber(BigDecimal.ZERO.subtract(number));
//				看是否是套餐明细菜，如果是则是0
				if(posDetailCopy.getFlag().substring(9, 10).equals("1")){
					posDetailCopy.setAmount(BigDecimal.ZERO);
				}else{
					posDetailCopy.setAmount(posDetailCopy.getNumber().multiply(posDetailCopy.getPrice()));	
				}
//				判断是否是单菜折扣或者赠送
				if((posDetailCopy.getFlag1().substring(0, 1).equals("1") && !posDetailCopy.getAmount5().equals(BigDecimal.ZERO)) || (posDetailCopy.getFlag1().substring(4, 5).equals("1") && !posDetailCopy.getAmount5().equals(BigDecimal.ZERO)) ){
//					BigDecimal b1 = new BigDecimal(1);
//					posDetailCopy.setDsc(posDetailCopy.getAmount().multiply(b1.subtract(posDetailCopy.getAmount5())));
					posDetailCopy.setDsc(posDetailCopy.getAmount().multiply(posDetailCopy.getAmount5()));
				}
				posDetailCopy.setShift(UserManager.getCashier().toString());
				posDetailCopy.setEmpid(UserManager.getUserCode());
				posDetailCopy.setBizDate(UserManager.getBizDate());
				posDetailCopy.setDescript("[退]"+posDetailCopy.getDescript());
				posDetailCopy.setDescriptEn("[退]"+posDetailCopy.getDescriptEn());
				posDetailCopy.setInfo(info);
				posDetailDao.save(posDetailCopy);
				
				ids = ids+posDetailCopy.getId()+",";
				
				int tnumdetail =posDetailGet.getTnumber();
				
				//套菜删除
				if(tnumdetail != 0){
					PosDetail posDetailsub= new PosDetail();
					posDetailsub.setHotelGroupId(hotelGroupId);
					posDetailsub.setHotelId(hotelId);
					posDetailsub.setAccnt(posMaster.getAccnt());
					posDetailsub.setTableno(tableNo);
					posDetailsub.setInumber(tnumdetail);
					List<PosDetail> list1sub = posDetailDao.list(posDetailsub);
					if(list1sub != null && list1sub.size()>0){
						
						for(Iterator<PosDetail> s =list1sub.iterator();s.hasNext(); ){
							PosDetail posDetailSubGet = s.next();
							PosDetail posDetailSubCopy = posDetailSubGet.cloneEx();
							posDetailSubCopy.setId(null);
							posDetailSubCopy.setHotelGroupId(null);
							posDetailSubCopy.setHotelId(null);
							posDetailSubCopy.setCreateDatetime(null);
							posDetailSubCopy.setCreateUser(null);
							posDetailSubCopy.setModifyUser(null);
							posDetailSubCopy.setModifyDatetime(null);
//							posDetailSubCopy.setNumber(BigDecimal.ZERO.subtract(number));
							posDetailSubCopy.setNumber(BigDecimal.ZERO.subtract(posDetailSubCopy.getNumber()));
							posDetailSubCopy.setAmount(BigDecimal.ZERO);
							posDetailSubCopy.setShift(UserManager.getCashier().toString());
							posDetailSubCopy.setEmpid(UserManager.getUserCode());
							posDetailSubCopy.setBizDate(UserManager.getBizDate());
							posDetailSubCopy.setDescript("[退]"+posDetailSubCopy.getDescript());
							posDetailSubCopy.setDescriptEn("[退]"+posDetailSubCopy.getDescriptEn());
							posDetailDao.save(posDetailSubCopy);
							
							ids = ids+posDetailSubCopy.getId()+",";
						}
						//posDetailDao.deleteAll(list1sub);
					}
				}
				
				
				
			}
			
			
		}
		
		
		PosAccess posAccess = new PosAccess();
		posAccess.setHotelGroupId(hotelGroupId);
		posAccess.setHotelId(hotelId);
		posAccess.setBizDate(UserManager.getBizDate());
		posAccess.setPluCode(currentPluCode);
		List<PosAccess> alist = posAccessDao.listPosAccessForOrder(posAccess);
		if(alist != null && alist.size()>0){
			PosAccess posAccessGet = alist.get(0);
			if(posAccessGet != null){
				posAccessGet.setNumber1(posAccessGet.getNumber1().subtract(num));
				posAccessDao.update(posAccessGet);
			}
		}
		
		return ids;
	}

	@Override
	public void updateCahngeNum(long hotelGroupId,long hotelId,PosMaster posMaster, String tableNo,String type, long id, String num) {
		 String currentPluCode = "";
		 String currentPludesc = "";
		 BigDecimal numc = BigDecimal.ZERO;
		
		if(type.equals("1")){
			//order
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setAccnt(posMaster.getAccnt());
			posOrder.setTableno(tableNo);
			posOrder.setId(id);
			List<PosOrder> list = posOrderDao.list(posOrder);
			if(list != null && list.size()>0){
				PosOrder posOrderGet = list.get(0);
				
				BigDecimal  numberBefor = posOrderGet.getNumber();  // 6
				BigDecimal  numberafter =new BigDecimal(num);  //1
				
				
				int tnum=posOrderGet.getTnumber();
				
				currentPluCode = posOrderGet.getPluCode();
				currentPludesc =  posOrderGet.getDescript();
				numc = new BigDecimal(num).subtract(posOrderGet.getNumber());
				
				posOrderGet.setNumber(new BigDecimal(num));
				//如果是套菜明细菜 修改数量 金额不变
				if(!posOrderGet.getFlag().substring(9,10).equals("1")){
				    posOrderGet.setAmount(posOrderGet.getNumber().multiply(posOrderGet.getPrice()));
				}
				posOrderDao.update(posOrderGet);
				
	
				
				
				//套菜修改
				if(tnum != 0){
					PosOrder posOrderSub = new PosOrder();
					posOrderSub.setHotelGroupId(hotelGroupId);
					posOrderSub.setHotelId(hotelId);
					posOrderSub.setAccnt(posMaster.getAccnt());
					posOrderSub.setTableno(tableNo);
					posOrderSub.setInumber(tnum);
					List<PosOrder> listsub = posOrderDao.list(posOrderSub);
					if(listsub != null && listsub.size()>0){
						for(Iterator<PosOrder> p = listsub.iterator();p.hasNext();){
							PosOrder posOrderSubGet = p.next();
							posOrderSubGet.setNumber(posOrderSubGet.getNumber().multiply(numberafter).divide(numberBefor));
							posOrderDao.update(posOrderSubGet);
						}
					}
				}
			}
			
		}else if(type.equals("0")){
			//detail
			PosDetail posDetail= new PosDetail();
			posDetail.setHotelGroupId(hotelGroupId);
			posDetail.setHotelId(hotelId);
			posDetail.setAccnt(posMaster.getAccnt());
			posDetail.setTableno(tableNo);
			posDetail.setId(id);
			List<PosDetail> list1 = posDetailDao.list(posDetail);
			if(list1 != null && list1.size()>0){
				PosDetail posDetailGet = list1.get(0);
				
				currentPluCode = posDetailGet.getCode();
				currentPludesc =  posDetailGet.getDescript();
				numc = new BigDecimal(num).subtract(posDetailGet.getNumber());
				
				posDetailGet.setNumber(new BigDecimal(num));
				//如果是套菜明细菜 修改数量 金额不变
				if(!posDetailGet.getFlag().substring(9,10).equals("1")){
					posDetailGet.setAmount(posDetailGet.getNumber().multiply(posDetailGet.getPrice()));
				}
				posDetailDao.update(posDetailGet);
			}
		}
		
		
		if(numc.compareTo(BigDecimal.ZERO) != 0){
			PosAccess posAccess = new PosAccess();
			posAccess.setHotelGroupId(hotelGroupId);
			posAccess.setHotelId(hotelId);
			posAccess.setBizDate(UserManager.getBizDate());
			posAccess.setPluCode(currentPluCode);
			List<PosAccess> alist = posAccessDao.listPosAccessForOrder(posAccess);
			if(alist != null && alist.size()>0){
				PosAccess posAccessGet = alist.get(0);
				if(posAccessGet != null){
					if(numc.compareTo(BigDecimal.ZERO)>0){
						//加数量 判断估清是否够
						if(posAccessGet.getNumber().subtract(posAccessGet.getNumber1()).subtract(numc).compareTo(BigDecimal.ZERO)>=0){
							posAccessGet.setNumber1(posAccessGet.getNumber1().add(numc));
							posAccessDao.update(posAccessGet);
						}else{
							throw new BizException("'"+currentPludesc+"'数量不足！",BizExceptionConstant.INVALID_PARAM);
						}
					}else if(numc.compareTo(BigDecimal.ZERO)<0){
						posAccessGet.setNumber1(posAccessGet.getNumber1().add(numc));
						posAccessDao.update(posAccessGet);
					}
				}
			}
		}
	}

	@Override
	public void updateCahngePrice(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo, String type, long id,String price) {
		if(type.equals("1")){
			//order
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setAccnt(posMaster.getAccnt());
			posOrder.setTableno(tableNo);
			posOrder.setId(id);
			List<PosOrder> list = posOrderDao.list(posOrder);
			if(list != null && list.size()>0){
				PosOrder posOrderGet = list.get(0);
				
				posOrderGet.setPrice(new BigDecimal(price));
				posOrderGet.setAmount(posOrderGet.getNumber().multiply(posOrderGet.getPrice()));
				posOrderDao.update(posOrderGet);
			}
			
		}else if(type.equals("0")){
			//detail
			PosDetail posDetail= new PosDetail();
			posDetail.setHotelGroupId(hotelGroupId);
			posDetail.setHotelId(hotelId);
			posDetail.setAccnt(posMaster.getAccnt());
			posDetail.setTableno(tableNo);
			posDetail.setId(id);
			List<PosDetail> list1 = posDetailDao.list(posDetail);
			if(list1 != null && list1.size()>0){
				PosDetail posDetailGet = list1.get(0);
				
				posDetailGet.setPrice(new BigDecimal(price));
				posDetailGet.setAmount(posDetailGet.getNumber().multiply(posDetailGet.getPrice()));
				posDetailDao.update(posDetailGet);
			}
		}
	}

	@Override
	public void updateCahngeDescript(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo, String type, long id,String descript) {
		if(type.equals("1")){
			//order
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setAccnt(posMaster.getAccnt());
			posOrder.setTableno(tableNo);
			posOrder.setId(id);
			List<PosOrder> list = posOrderDao.list(posOrder);
			if(list != null && list.size()>0){
				PosOrder posOrderGet = list.get(0);
				
				posOrderGet.setDescript(descript);
				posOrderGet.setDescriptEn(descript);
				posOrderDao.update(posOrderGet);
			}
			
		}else if(type.equals("0")){
			//detail
			PosDetail posDetail= new PosDetail();
			posDetail.setHotelGroupId(hotelGroupId);
			posDetail.setHotelId(hotelId);
			posDetail.setAccnt(posMaster.getAccnt());
			posDetail.setTableno(tableNo);
			posDetail.setId(id);
			List<PosDetail> list1 = posDetailDao.list(posDetail);
			if(list1 != null && list1.size()>0){
				PosDetail posDetailGet = list1.get(0);
				
				posDetailGet.setDescript(descript);
				posDetailGet.setDescriptEn(descript);
				posDetailDao.update(posDetailGet);
			}
		}
	}

	@Override
	public void updateCahngeDescriptOr(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo, String type, long id,String descript,String num,String price,String unit) {
		updateCahngeNum(hotelGroupId,hotelId,posMaster,tableNo,type,id,num);
		if(type.equals("1")){
			//order
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setAccnt(posMaster.getAccnt());
			posOrder.setTableno(tableNo);
			posOrder.setId(id);
			List<PosOrder> list = posOrderDao.list(posOrder);
			if(list != null && list.size()>0){
				PosOrder posOrderGet = list.get(0);
				
				posOrderGet.setDescript(descript);
				posOrderGet.setDescriptEn(descript);
				posOrderGet.setPrice(new BigDecimal(price));
				if(unit != null && !unit.equals("")){
					posOrderGet.setUnit(unit);
				}
				
				posOrderGet.setAmount(posOrderGet.getNumber().multiply(posOrderGet.getPrice()));			
				
				posOrderDao.update(posOrderGet);
			}
			
		}else if(type.equals("0")){
			//detail
			PosDetail posDetail= new PosDetail();
			posDetail.setHotelGroupId(hotelGroupId);
			posDetail.setHotelId(hotelId);
			posDetail.setAccnt(posMaster.getAccnt());
			posDetail.setTableno(tableNo);
			posDetail.setId(id);
			List<PosDetail> list1 = posDetailDao.list(posDetail);
			if(list1 != null && list1.size()>0){
				PosDetail posDetailGet = list1.get(0);
				
				posDetailGet.setDescript(descript);
				posDetailGet.setDescriptEn(descript);
				posDetailGet.setPrice(new BigDecimal(price));
				if(unit != null && !unit.equals("")){
					posDetailGet.setUnit(unit);
				}
				posDetailGet.setAmount(posDetailGet.getNumber().multiply(posDetailGet.getPrice()));
				posDetailDao.update(posDetailGet);
			}
		}
	}
	
	@Override
	public void updateFreePlu(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo, String type, long id,String info) {
		PosDetail posDetail= new PosDetail();
		posDetail.setHotelGroupId(hotelGroupId);
		posDetail.setHotelId(hotelId);
		posDetail.setAccnt(posMaster.getAccnt());
		posDetail.setTableno(tableNo);
		posDetail.setId(id);
		List<PosDetail> list1 = posDetailDao.list(posDetail);
		if(list1 != null && list1.size()>0){
			PosDetail posDetailGet = list1.get(0);
			posDetailGet.setReason(info);
			posDetailGet.setAmount5(BigDecimal.ONE);
			posDetailGet.setDsc(posDetailGet.getAmount());
			
			String flag1=posDetailGet.getFlag1();
			if(flag1 != null && flag1.length()==10){
				String s1=flag1.substring(0, 4);
				String s2=flag1.substring(5);
				String s3=s1+"1"+s2;
				posDetailGet.setFlag1(s3);
				
			}
			
			posDetailDao.update(posDetailGet);
		}
	}

	@Override
	public void updateCancleFreePlu(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo, String type, long id,String info) {
		PosDetail posDetail= new PosDetail();
		posDetail.setHotelGroupId(hotelGroupId);
		posDetail.setHotelId(hotelId);
		posDetail.setAccnt(posMaster.getAccnt());
		posDetail.setTableno(tableNo);
		posDetail.setId(id);
		List<PosDetail> list1 = posDetailDao.list(posDetail);
		if(list1 != null && list1.size()>0){
			PosDetail posDetailGet = list1.get(0);
			posDetailGet.setReason("");
			posDetailGet.setAmount5(BigDecimal.ZERO);
			posDetailGet.setDsc(BigDecimal.ZERO);
			
			String flag1=posDetailGet.getFlag1();
			if(flag1 != null && flag1.length()==10){
				String s1=flag1.substring(0, 4);
				String s2=flag1.substring(5);
				String s3=s1+"0"+s2;
				posDetailGet.setFlag1(s3);
				
			}
			
			posDetailDao.update(posDetailGet);
		}
	}

	@Override
	public void updateDecPlu(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo, String type, long id,String reason, String amount5, String dsc) {
		PosDetail posDetail= new PosDetail();
		posDetail.setHotelGroupId(hotelGroupId);
		posDetail.setHotelId(hotelId);
		posDetail.setAccnt(posMaster.getAccnt());
		posDetail.setTableno(tableNo);
		posDetail.setId(id);
		List<PosDetail> list1 = posDetailDao.list(posDetail);
		if(list1 != null && list1.size()>0){
			PosDetail posDetailGet = list1.get(0);
			//单菜免服务费
			if(type.equalsIgnoreCase("srv")){
				posDetailGet.setInfo(reason);
				posDetailGet.setSrv(BigDecimal.ZERO);
				posDetailGet.setSrv0(BigDecimal.ZERO);
				String flag1=posDetailGet.getFlag1();
				if(flag1 != null && flag1.length()==10){
					if(reason.equalsIgnoreCase("revokeFreeSrv")){
						flag1 = flag1.substring(0, 5)+"0"+flag1.substring(6);
					}else{
					    flag1 = flag1.substring(0, 5)+"1"+flag1.substring(6);
					}
					posDetailGet.setFlag1(flag1);
				}	
			}else{
				posDetailGet.setReason(reason);
				posDetailGet.setAmount5(new BigDecimal(amount5));
				posDetailGet.setDsc(new BigDecimal(dsc));
				
				String flag1=posDetailGet.getFlag1();
				if(flag1 != null && flag1.length()==10){
					String s1=flag1.substring(0, 1);
					String s2=flag1.substring(1);
					
					if(new BigDecimal(dsc).compareTo(BigDecimal.ZERO) == 0){
						s1="0";
						posDetailGet.setReason("");
					}else{
						s1="1";
					}
					
					String s3=s1+s2;
					posDetailGet.setFlag1(s3);
					
				}
			}
			posDetailDao.update(posDetailGet);
		}
	}

	@Override
	public void updatePluCook(long hotelGroupId, long hotelId,PosMaster posMaster, String tableNo, String type, long id,String codes, String cook,String remark) {
		if(type.equals("1")){
			//order
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setAccnt(posMaster.getAccnt());
			posOrder.setTableno(tableNo);
			posOrder.setId(id);
			List<PosOrder> list = posOrderDao.list(posOrder);
			if(list != null && list.size()>0){
				PosOrder posOrderGet = list.get(0);
				
				posOrderGet.setCondCode(codes);
				posOrderGet.setCook(cook);
				posOrderGet.setRemark(remark);
				posOrderDao.update(posOrderGet);
			}
			
		}else if(type.equals("0")){
			//detail
			PosDetail posDetail= new PosDetail();
			posDetail.setHotelGroupId(hotelGroupId);
			posDetail.setHotelId(hotelId);
			posDetail.setAccnt(posMaster.getAccnt());
			posDetail.setTableno(tableNo);
			posDetail.setId(id);
			List<PosDetail> list1 = posDetailDao.list(posDetail);
			if(list1 != null && list1.size()>0){
				PosDetail posDetailGet = list1.get(0);
				
				posDetailGet.setCondCode(codes);
				posDetailGet.setCook(cook);
				posDetailDao.update(posDetailGet);
			}
		}
	}
	
	private CodeTransaction getCodeTransactionByCode(long hotelGroupId, long hotelId,String taCode){
		CodeTransaction codeTransaction = new CodeTransaction();
		codeTransaction.setHotelGroupId(hotelGroupId);
		codeTransaction.setHotelId(hotelId);
		codeTransaction.setCode(taCode);
		List<CodeTransaction> list = codeTransactionDao.list(codeTransaction);
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	
	public String genBillNo(String code,String type,Date bizDate,Long hotelGroupId,Long hotelId){
		
	    // 默认模式 yyMMdd+XXXX
		// 计算预订号
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String timestamp = sdf.format(bizDate);
		String posCur = posResDao.findSysExtraIdByProc(code, hotelGroupId, hotelId);
		if(posCur.length() > 16){
			throw new BizException("sysExtraId数据配置错误,code=["+code+"]hotelGroupId=["+hotelGroupId+"]hotelId=["+hotelId+"]\n"+posCur,
					BizExceptionConstant.INVALID_PARAM,BizException.BIZ_TYPE.TYPE_1);
		}
		int tempInt = 4 - posCur.length();
		// 前补位
		for (int i = 0; i < tempInt; i++) {
			posCur = "0" + posCur;
		}
		if(StringHelper.isNotNull(type)){
			return type + timestamp + posCur;
		}
		return timestamp + posCur;
}
	
	
	private String getTaCode(long hotelGroupId, long hotelId,String pcCode,String shift){
		PosPccodeShift posPccodeShift = new PosPccodeShift();
		posPccodeShift.setHotelGroupId(hotelGroupId);
		posPccodeShift.setHotelId(hotelId);
		posPccodeShift.setPccode(pcCode);
		posPccodeShift.setShift(shift);
		List<PosPccodeShift> list = posPccodeShiftDao.list(posPccodeShift);
		if(list != null && list.size()>0){
			return list.get(0).getTaCode();
		}else{
			return "";
		}
	}
	
	@Override
	public String updateCheckOut(long hotelGroupId, long hotelId,String coi,PosMaster posMaster, String tableNo, String accnts, String taCode,BigDecimal pay, String reason,String reasonDesc,String cardno, String remark,String balance) {
		String cardnoNew = "";
		BigDecimal chargeTotal = new BigDecimal(0);
		//首检查付款码是否存在
		CodeTransaction codeTransaction = this.getCodeTransactionByCode(hotelGroupId, hotelId, taCode);
		if(codeTransaction == null){
			throw new BizException("付款码不存在，请检查配置！",BizExceptionConstant.INVALID_PARAM);
		}
		if("0".equals(UserManager.getCashier().toString())){
			throw new BizException("系统班次为0，不允许结账，请退出系统重新登入！",BizExceptionConstant.INVALID_PARAM);
		}
		if("".equals(this.getTaCode(hotelGroupId, hotelId, posMaster.getPccode(), UserManager.getCashier().toString()))){
			throw new BizException("营业点班次对应费用码未定义，不能结账！",BizExceptionConstant.INVALID_PARAM);
		}
		
		//结账前先检查主单状态，当心有其他站点进行了消单或已经结账操作
		PosMaster posMasterSet = new PosMaster();
		posMasterSet.setHotelGroupId(hotelGroupId);
		posMasterSet.setHotelId(hotelId);
		posMasterSet.setAccnt(posMaster.getAccnt());
		List<PosMaster> posMasterSetList = posMasterDao.list(posMasterSet);
		if(posMasterSetList != null && posMasterSetList.size() > 0){			
			if(posMasterSetList.get(0).getSta().equals("X")){
				throw new BizException("该主单状态已经是消单状态，不能再结账", BizExceptionConstant.INVALID_PARAM);
			}
			if(posMasterSetList.get(0).getSta().equals("O")){
				throw new BizException("该主单状态已经处于结账状态，不能再结账", BizExceptionConstant.INVALID_PARAM);
			}
			cardnoNew = posMasterSetList.get(0).getCardno();
		}
		String inerfaceMode = "";
		SysOption sysOptionKaiyuan =  sysOptionService.findSysOptionByCatalogItem("member", "interface_mod", hotelGroupId, hotelId);
		if(sysOptionKaiyuan != null && sysOptionKaiyuan.getSetValue() != null){
			inerfaceMode = sysOptionKaiyuan.getSetValue();
		}
		String pmsPaycode = "";
		SysOption sysOptionpay =  sysOptionService.findSysOptionByCatalogItem("pos", "paycode_need_reation_pms", hotelGroupId, hotelId);
		if(sysOptionpay != null && sysOptionpay.getSetValue() != null){
			pmsPaycode = sysOptionpay.getSetValue();
		}
		String billNo="";
		//param coi  标志 0 入账并结账，1入账
		
		//入account
		if(pay.compareTo(BigDecimal.ZERO) != 0){
			
			PosAccount  posAccount = new PosAccount();
			posAccount.setHotelGroupId(hotelGroupId);
			posAccount.setHotelId(hotelId);
			posAccount.setAccnt(posMaster.getAccnt());
			posAccount.setNumber(2);
			posAccount.setInumber(0);
			posAccount.setSubid(0);
			posAccount.setShift(UserManager.getCashier().toString());
			posAccount.setPccode(posMaster.getPccode());
			posAccount.setTableno(tableNo);
			posAccount.setEmpid(UserManager.getUserCode());
			posAccount.setBizDate(UserManager.getBizDate());
			posAccount.setLogdate(new Date());
			posAccount.setPaycode(codeTransaction.getCode());
			posAccount.setDescript(codeTransaction.getDescript());
			posAccount.setDescriptEn(codeTransaction.getDescriptEn());
			posAccount.setAmount(BigDecimal.ZERO);
			posAccount.setCredit(pay);
			posAccount.setBal(BigDecimal.ZERO);
			posAccount.setBillno("");
			posAccount.setFoliono("");
			posAccount.setOrderno("");
			posAccount.setSign("");
			posAccount.setFlag("");
			posAccount.setSta("I");
			posAccount.setReason(reason);
			posAccount.setInfo1(remark);
			posAccount.setInfo2(reasonDesc);
			posAccount.setBank(balance);
			posAccount.setCardno(cardno);
			posAccount.setDtlAccnt(posMaster.getResno());
			posAccountDao.save(posAccount);
			//如果付款方式是冲预付,修改预付定金posPay
			if(codeTransaction.getCatPosting().equalsIgnoreCase("CDJ")){
				//reason = "CDJ"  reasonDesc = posPay.accnt+","+posPay.foliono+","+posPay.credit;
				PosPay posPay = new PosPay();
				posPay.setHotelGroupId(hotelGroupId);
				posPay.setHotelId(hotelId);
				posPay.setAccnt(reasonDesc.split(",")[0]);
				posPay.setSta("I");
				posPay.setFoliono(reasonDesc.split(",")[1]);
				posPay.setCredit(new BigDecimal(reasonDesc.split(",")[2]));
				List<PosPay> payList = posPayDao.list(posPay);
				if(payList!=null && payList.size()>0){
					PosPay posPayChoose = payList.get(0);
					// SET  trand = 'T',taccnt = arg_accnt ,tshift = arg_shift,sta= 'O' ,tempid = arg_user, tbdate = var_date,dtl_accnt = arg_accnt 
					posPayChoose.setSta("O");
					posPayChoose.setTrand("T");
					posPayChoose.setTaccnt(posMaster.getAccnt());
					posPayChoose.setTshift(UserManager.getCashier().toString());
					posPayChoose.setTempid(UserManager.getUserCode());
					posPayChoose.setTbdate(UserManager.getBizDate());
					posPayChoose.setReason("CDJ");
					posPayChoose.setDtlAccnt(posMaster.getAccnt());
					posPayDao.update(posPayChoose);
				}
				//插入同步信息表 订单需要同步
				PosAccntSync ls = new PosAccntSync();
				ls.setHotelGroupId(hotelGroupId);
				ls.setHotelId(hotelId);
				ls.setAccnt(posMaster.getAccnt());
				ls.setResAccnt(posPay.getAccnt());
				ls.setEntityName("com.greencloud.entity.PosMaster");
				ls.setType("UPDATE");
				ls.setIsHalt("F");
				ls.setIsSync("F");
				ls.setCreateUser(UserManager.getUserCode());
				ls.setModifyUser(UserManager.getUserCode());								
				posAccntSyncDao.save(ls);	
			}else{
			
				String aliCode="";
				String wxCode="";
				SysOption sysOption1 =  sysOptionService.findSysOptionByCatalogItem("scanpay", "alipay_pay_code", hotelGroupId, hotelId);
				SysOption sysOption2 =  sysOptionService.findSysOptionByCatalogItem("scanpay", "wx_pay_code", hotelGroupId, hotelId);
				
				if(sysOption1 != null && sysOption1.getSetValue() != null){
					aliCode=sysOption1.getSetValue();
				}
				
				if(sysOption2 != null && sysOption2.getSetValue() != null){
					wxCode=sysOption2.getSetValue();
				}
				
				if(codeTransaction.getCode().equals(aliCode)  || codeTransaction.getCode().equals(wxCode)){
						String bdesc="";
				    	String scanChannel= "";
						if(codeTransaction.getCode().equals(aliCode) ){
							scanChannel="ALIPAY";
							bdesc = "支付宝扫码-"+UserManager.getHotelDescript();
						}
						if(codeTransaction.getCode().equals(wxCode)){
							scanChannel="WEIXIN";
							bdesc = "微信扫码-"+UserManager.getHotelDescript();
						}
					
						String pmsIp0 = getRemoteIp(hotelGroupId,hotelId);
						if(pmsIp0 != null && !pmsIp0.equals("")){
							IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp0, IPmsPosFacadeService.class);
							try {
								String result=service.saveScanAccount(scanChannel, hotelGroupId, hotelId, cardno, bdesc, "", posMaster.getId(), posMaster.getAccnt(), pay);
								
								if(result !=null && !result.equals("")){
									String [] sarr=result.split(",");
									if(sarr[0] != null && sarr[0].equals("1")){
										String outId=sarr[1];
										String tradeNo=sarr[2];
										
										posAccount.setFoliono(outId);
										posAccount.setOrderno(tradeNo);
										posAccountDao.update(posAccount);
									}else{
										 throw new BizException("扣款请求失败...",BizExceptionConstant.INVALID_PARAM);
									}
								}
								
							} catch (Exception e) {
								e.printStackTrace();
								 throw new BizException("扣款请求失败...",BizExceptionConstant.INVALID_PARAM);
							}
						}else{
							 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
						}
						
				}
			}
//			付款方式是会员卡的，如果主单上没有卡号,则将卡号保存到主单中
//			if(codeTransaction.getCatPosting().equals("RCV")){
//				if((posMasterSetList.get(0).getCardno() == null || "".equals(posMasterSetList.get(0).getCardno()))){
//					cardnoNew = reasonDesc.substring(1,reasonDesc.indexOf("]"));
//					if(posMasterSetList.get(0).getPcrec() == null || "".equals(posMasterSetList.get(0).getPcrec())){
//						PosMaster posMasterNewSet = new PosMaster();
//						posMasterNewSet = posMasterSetList.get(0);				
//						posMasterNewSet.setCardno(cardnoNew);
//						posMasterDao.update(posMasterNewSet);
//					}else{
//						PosMaster posMasterNewSet = new PosMaster();
//						posMasterNewSet.setHotelGroupId(hotelGroupId);
//						posMasterNewSet.setHotelId(hotelId);
//						posMasterNewSet.setPcrec(posMasterSetList.get(0).getPcrec());
//						List<PosMaster> posMasterSetNewList = posMasterDao.list(posMasterNewSet);
//						if(posMasterSetNewList != null && posMasterSetNewList.size() > 0){
//							for(Iterator<PosMaster> i=posMasterSetNewList.iterator();i.hasNext();){
//								PosMaster posMasterNewGet = i.next();									
//								posMasterNewGet.setCardno(cardnoNew);
//								posMasterDao.update(posMasterNewGet);
//							}
//						}
//					}
//				}																									
//			}
		
		}
		
		if(coi != null && coi.equals("0")){
			//结账
			//第一步 再检查一次 数据 是否金额一致
			PosMasterDto posM = new PosMasterDto();
			posM = posMasterService.updateGetPosMasterDtoForCo2(hotelGroupId, hotelId, posMaster.getAccnt(), tableNo, UserManager.getBizDate());
			if(posM.getAmount().add(posM.getSrvamount()).add(posM.getTaxamount()).subtract(posM.getDscamount()).subtract(posM.getCredit()).compareTo(pay)==0){
				chargeTotal = posM.getAmount().add(posM.getSrvamount()).add(posM.getTaxamount()).subtract(posM.getDscamount());
				//第一步  插入pos_close
				// 账单编号格式：B+营业点+时间+流水号
				String billno = this.genBillNo("POSBILL", "B"+posMaster.getPccode(), UserManager.getBizDate(), hotelGroupId, hotelId);
//				String billno = this.genBillNo("POSBILL", "B", UserManager.getBizDate(), hotelGroupId, hotelId);
				PosClose posClose = new PosClose();
				posClose.setHotelGroupId(hotelGroupId);
				posClose.setHotelId(hotelId);
				posClose.setAccnt(posMaster.getAccnt());
				posClose.setBillno(billno);
				posClose.setCharge(pay);
				posClose.setPay(pay);
				posClose.setTransType("");
				posClose.setTransAccnt("");
				posClose.setGenBizDate(UserManager.getBizDate());
				posClose.setGenUser(UserManager.getUserCode());
				posClose.setGenCashier(UserManager.getCashier().toString());
				posClose.setGenDatetime(new Date());
				posClose.setDelBizDate(null);
				posClose.setDelCashier(null);
				posClose.setDelDatetime(null);
				posClose.setDelUser(null);
				posClose.setTableno(tableNo);
				posClose.setAuditUser(null);
				posClose.setAuditRemark(null);
				posCloseDao.save(posClose);
				
				
				
				//第二步调用结账过程
				int res=posMasterDao.updatePosCheckOut(hotelGroupId, hotelId, UserManager.getBizDate(), UserManager.getCashier().toString(), accnts, billno, UserManager.getUserCode());
				
				if(res==-1){
					throw new BizException("结账调用出错，请检查！",BizExceptionConstant.INVALID_PARAM);
				}
				billNo = billno;
			}else{
				throw new BizException("付款金额与消费金额不一致,请刷新主单重新结账！",BizExceptionConstant.INVALID_PARAM);
			}
		}
//		将调用pms入账的程序放在最后，以避免餐饮结账失败，但是前台却已经入账成功的问题
		if(pay.compareTo(BigDecimal.ZERO) != 0){
//			if(sysOptionKaiyuan != null && "KAIYUAN".equals(inerfaceMode) && codeTransaction.getCatPosting().equals("RCV")){
			if(codeTransaction.getCatPosting().equals("KYK")){
				if("0".equalsIgnoreCase(coi)){
					String ret = kaiYuanInterfaceService.updateCardConsume(hotelGroupId, hotelId, UserManager.getBizDate(), billNo, pay,"SV_REDEMPTION",cardno,coi,posMaster.getGsts(),UserManager.getHotelCode(),posMaster.getAccnt(),posMaster.getPccode());
					if(!"1".equals(ret)){
						throw new BizException("会员引起的结账扣款失败，请检查！",BizExceptionConstant.INVALID_PARAM);
					}
				}else{
					String ret = kaiYuanInterfaceService.updateCardConsume(hotelGroupId, hotelId, UserManager.getBizDate(), posMaster.getAccnt(), pay,"SV_REDEMPTION",cardno,coi,posMaster.getGsts(),UserManager.getHotelCode(),posMaster.getAccnt(),posMaster.getPccode());
					if(!"1".equals(ret)){
						throw new BizException("会员引起的结账扣款失败，请检查！",BizExceptionConstant.INVALID_PARAM);
					}
				}				
			}else if(codeTransaction.getCatPosting() != null && (codeTransaction.getCatPosting().equals("TF") || codeTransaction.getCatPosting().equals("TA") || codeTransaction.getCatPosting().equals("RCV")) || codeTransaction.getCatPosting().equals("RCP")
						|| (StringUtil.iContains(pmsPaycode,codeTransaction.getCatPosting()))){
				String transInfo = "";
				String taCodeTrans = this.getTaCode(hotelGroupId, hotelId, posMaster.getPccode(), UserManager.getCashier().toString());
				CodeTransaction codeTransactiontaCodeTrans = this.getCodeTransactionByCode(hotelGroupId, hotelId, taCodeTrans);
				if(codeTransactiontaCodeTrans == null){
					throw new BizException("转账费用码不存在，请检查配置！",BizExceptionConstant.INVALID_PARAM);
				}
												
				//转前台
				PosUserDto posUserDto = new PosUserDto();
				posUserDto.setBizDate(UserManager.getBizDate());
				posUserDto.setCashier(UserManager.getCashier().toString());
				posUserDto.setHotelGroupId(hotelGroupId);
				posUserDto.setHotelId(hotelId);
				posUserDto.setPcCode(posMaster.getPccode());
				posUserDto.setTaCode(taCodeTrans);
				posUserDto.setUserCode(UserManager.getUserCode());
				if(posMasterSetList.get(0).getCardno() != null){
					posUserDto.setCardNo(posMasterSetList.get(0).getCardno());
				}else{
					posUserDto.setCardNo("");
				}
				
				List<PosPayDto> listPay = new ArrayList<PosPayDto>();
				PosPayDto posPayDto = new PosPayDto();
				posPayDto.setAccnt(Long.parseLong(cardno));
//				if(posMasterSetList.get(0).getCardno() != null){
//					posPayDto.setCardNo(posMasterSetList.get(0).getCardno());
//				}else{
//					posPayDto.setCardNo("");
//				}				
				posPayDto.setCode(codeTransaction.getCode());
				posPayDto.setDescript(codeTransaction.getDescript());
				posPayDto.setPasswd("");
				posPayDto.setPay(pay);
				posPayDto.setReceipt(BigDecimal.ZERO);
				posPayDto.setRemark(remark);
				posPayDto.setSysOrder(null);
				if(reasonDesc.indexOf("[") >= 0){
					String remarkNew = reasonDesc.substring(reasonDesc.indexOf("[")+1, reasonDesc.indexOf("]"));
					posPayDto.setCardNo(remarkNew);
				}else{
					posPayDto.setCardNo(reasonDesc);
				}
				listPay.add(posPayDto);
				
				PosPccode posPccodeSet = new PosPccode();
				posPccodeSet.setHotelGroupId(hotelGroupId);
				posPccodeSet.setHotelId(hotelId);
				posPccodeSet.setCode(posMaster.getPccode());
				List<PosPccode> posPccodeGet = posPccodeDao.list(posPccodeSet);
				if(posPccodeGet != null && posPccodeGet.size() > 0){
					transInfo = "["+posPccodeGet.get(0).getDescript()+"]-桌号/包厢:"+tableNo;					
				}else{
					transInfo = "桌号/包厢:"+tableNo;
				}
				
				String pmsIp = getRemoteIp(hotelGroupId,hotelId);
				if(pmsIp != null && !pmsIp.equals("")){
					IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
					service.updatePosAccntIn(posUserDto, posMaster.getAccnt(), tableNo, listPay, transInfo);
				}else{
					 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
				}					
			}		
		}
//		结完帐
		if(coi != null && coi.equals("0")){
//			 && codeTransaction.getCatPosting().equals("RCV")
			if("KAIYUAN".equals(inerfaceMode) && !"".equals(posMaster.getCardno())){
				String menuNo = "";
				if(posMaster.getPcrec() != null && !"".equals(posMaster.getPcrec())){
					menuNo = posMaster.getPcrec();
				}else{
					menuNo = posMaster.getAccnt();
				}
				String ret = kaiYuanInterfaceService.updateCardConsume(hotelGroupId, hotelId, UserManager.getBizDate(), billNo, pay,"SV_REDEMPTION_C",posMaster.getCardno(),coi,posMaster.getGsts(),UserManager.getHotelCode(),menuNo,posMaster.getPccode());
				if(!"1".equals(ret)){
					throw new BizException("会员引起的结账传消费失败，请检查！",BizExceptionConstant.INVALID_PARAM);
				}
			}
		}
//		if(pay.compareTo(BigDecimal.ZERO) != 0){
//			if("KAIYUAN".equals(sysOptionKaiyuan.getSetValue()) && codeTransaction.getCatPosting().equals("RCV")){
//				kaiYuanInterfaceService.updateCardConsume(hotelGroupId, hotelId, UserManager.getBizDate(), billNo, pay,"SV_REDEMPTION");
//			}			
//		}
		
//		积分统计if(sysOptionKaiyuan != null && "KAIYUAN".equals(sysOptionKaiyuan.getSetValue())
		if(coi != null && coi.equals("0") && !"".equals(cardnoNew) && chargeTotal.compareTo(BigDecimal.ZERO) != 0 && !"KAIYUAN".equals(inerfaceMode)){
			String cTaCode = "";	
			PosUserDto posUserDto = new PosUserDto();
			List<PosMemberPointDto> posMemberPointList = new ArrayList<PosMemberPointDto>();
			
			cTaCode = this.getTaCode(hotelGroupId, hotelId, posMaster.getPccode(), UserManager.getCashier().toString());
			CodeTransaction cGet =  this.getCodeTransactionByCode(hotelGroupId, hotelId, cTaCode);
			if(cGet == null){
				throw new BizException("营业点对应的消费码不存在！",BizExceptionConstant.INVALID_PARAM);
			}else{				
				posUserDto.setBizDate(UserManager.getBizDate());
				posUserDto.setCashier(UserManager.getCashier().toString());
				posUserDto.setHotelGroupId(hotelGroupId);
				posUserDto.setHotelId(hotelId);
				posUserDto.setUserCode(UserManager.getUserCode());
												
				PosMemberPointDto posMemberPoint = new PosMemberPointDto();
				posMemberPoint.setHotelGroupId(hotelGroupId);
				posMemberPoint.setHotelId(hotelId);
				posMemberPoint.setBizDate(UserManager.getBizDate());
				posMemberPoint.setAccnt(posMaster.getAccnt());
				posMemberPoint.setCardNo(cardnoNew);
				posMemberPoint.setMarket(posMaster.getMarket());
				posMemberPoint.setSrc(posMaster.getSource());
				posMemberPoint.setPosMode(posMaster.getMode());
				posMemberPoint.setTableno("桌号:"+tableNo);
				posMemberPoint.setbDate(UserManager.getBizDate());
				posMemberPoint.setTaCode(cGet.getCode());
				posMemberPoint.setTaDescript(cGet.getDescript());
				posMemberPoint.setTaDescriptEn(cGet.getDescriptEn());
				posMemberPoint.setCharge(chargeTotal);
				posMemberPoint.setPay(chargeTotal);
				posMemberPoint.setNumber(new Long(1));
				posMemberPoint.setCashier(UserManager.getCashier());
				posMemberPoint.setRemark("");
				posMemberPoint.setCreateUser(UserManager.getUserCode());
				posMemberPoint.setPayCode(codeTransaction.getCode());
				
				posMemberPointList.add(posMemberPoint);
			}
			
			String pmsIp = getRemoteIp(hotelGroupId,hotelId);
			if(pmsIp != null && !pmsIp.equals("")){
				IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
				service.savePosMemberPoint(posUserDto, posMemberPointList);
			}else{
				 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
			}	
		}
		
		return billNo;
	}

	public <T> T findInterfaceWithIp(String ip,Class<T> class1) {
		 String className = class1.getName();
	        if (className.lastIndexOf(".") > -1) {
	            className = className.substring(className.lastIndexOf(".") + 1);
	        }

	        // FICommonFacadeService
	        if (className.startsWith("FI")) {
	            className = Character.toLowerCase(className.charAt(0)) + className.substring(2);
	        } else {
	            className = Character.toLowerCase(className.charAt(1)) + className.substring(2);
	        }

	        if (ip != null) {
	            if (ip.indexOf("http://") == -1) {
	                ip = "http://" + ip;
	            }
	            String url = ip + "/";
	            url = url + className;
	            
	            
	            HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
	            
	            HessianProxyFactory hpf = new HessianProxyFactory();
	            hpf.setOverloadEnabled(true);
	            
	            factory.setProxyFactory(hpf);
	            factory.setServiceUrl(url);
	            factory.setServiceInterface(class1);
	            factory.afterPropertiesSet();
	            return (T)factory.getObject();
	        }
	        
	        return null;
        
//        throw new BizException("参数错误.",BizExceptionConstant.INVALID_PARAM);
    }

	@Override
	public List<PosOrder> saveCachePosOrder(long hotelGroupId, long hotelId,String accnt, String tableNo, List<PosOrder> list) {
		PosMaster posMaster = this.getPosMasterByAccnt(hotelGroupId, hotelId,accnt);
		if(posMaster != null && list != null){
			for(Iterator<PosOrder> j = list.iterator();j.hasNext();){
				PosOrder posOrderGet = j.next();
				posOrderGet.setHotelGroupId(hotelGroupId);
				posOrderGet.setHotelId(hotelId);
				posOrderGet.setPcid(UserManager.getWorkStationId().toString());
				posOrderGet.setAccnt(posMaster.getAccnt());
				posOrderGet.setInumber(0);
				posOrderGet.setTnumber(posMaster.getLastnum()+1);
				posOrderGet.setAnumber(0);
				posOrderGet.setMnumber(0);
				posOrderGet.setType("1");
				posOrderGet.setSaleid(posMaster.getSaleid());
				posOrderGet.setTableno(tableNo);
				posOrderGet.setEmpid1(posMaster.getEmpid());
				posOrderDao.save(posOrderGet);
				
				posMaster.setLastnum(posMaster.getLastnum()+1);
				posMasterDao.update(posMaster);
				
				PosAccess posAccess = new PosAccess();
				posAccess.setHotelGroupId(hotelGroupId);
				posAccess.setHotelId(hotelId);
				posAccess.setBizDate(UserManager.getBizDate());
				posAccess.setPluCode(posOrderGet.getPluCode());
				List<PosAccess> alist = posAccessDao.listPosAccessForOrder(posAccess);
				if(alist != null && alist.size()>0){
					PosAccess posAccessGet = alist.get(0);
					if(posAccessGet != null){
						if(posAccessGet.getNumber().subtract(posAccessGet.getNumber1()).subtract(posOrderGet.getNumber()).compareTo(BigDecimal.ZERO)>=0){
							posAccessGet.setNumber1(posAccessGet.getNumber1().add(posOrderGet.getNumber()));
							posAccessDao.update(posAccessGet);
						}else{
							throw new BizException("'"+posOrderGet.getDescript()+"'数量不足！",BizExceptionConstant.INVALID_PARAM);
						}
					}
				}
				
				PosPluAll posPluAll = this.getPosPluAllByCode(hotelGroupId, hotelId, posOrderGet.getPluCode());
				
				if(posPluAll != null && posPluAll.getFlag() != null && !posPluAll.getFlag().equals("") && posPluAll.getFlag().length()>0){
					String taocaiFlag = posPluAll.getFlag().substring(0, 1);
					if(taocaiFlag != null && taocaiFlag.equals("1")){
						//套餐
						PosPluStd  posPluStd = new PosPluStd();
						posPluStd.setHotelGroupId(hotelGroupId);
						posPluStd.setHotelId(hotelId);
						posPluStd.setCode(posPluAll.getCode());
						posPluStd.setIsHalt("F");
						List<PosPluStd> subList = posPluStdDao.list(posPluStd);
						if(subList != null && subList.size()>0){
							for(Iterator<PosPluStd> i=subList.iterator();i.hasNext();){
								PosPluStd posPluStdGet = i.next();
								PosPluAll posPluAllSub = this.getPosPluAllByCode(hotelGroupId, hotelId, posPluStdGet.getPluCode());
								if(posPluAllSub != null){
									PosOrder posOrderSub = new PosOrder();
									posOrderSub.setHotelGroupId(hotelGroupId);
									posOrderSub.setHotelId(hotelId);
									posOrderSub.setPcid(UserManager.getWorkStationId().toString());
									posOrderSub.setAccnt(posMaster.getAccnt());
									posOrderSub.setInumber(posOrderGet.getTnumber());
									posOrderSub.setTnumber(posMaster.getLastnum()+1);
									posOrderSub.setAnumber(0);
									posOrderSub.setMnumber(0);
									posOrderSub.setType("1");
									posOrderSub.setOrderno("0");
									posOrderSub.setCondCode("");
									posOrderSub.setDescript(posPluAllSub.getDescript());
									posOrderSub.setDescriptEn(posPluAllSub.getDescriptEn());
									posOrderSub.setNoteCode("");
									posOrderSub.setSortCode(posPluAllSub.getSortCode());
									posOrderSub.setPluCode(posPluAllSub.getCode());
									posOrderSub.setTocode(posPluAllSub.getTocode());
									posOrderSub.setUnit(posPluStdGet.getUnit());
									posOrderSub.setNumber(posPluStdGet.getNumber().multiply(posOrderGet.getNumber()));
									posOrderSub.setPrice(posPluStdGet.getPrice0());
									posOrderSub.setAmount(BigDecimal.ZERO);
									posOrderSub.setSta("I");
									posOrderSub.setFlag(posPluStdGet.getFlag());
									posOrderSub.setFlag1("0000000000");
									posOrderSub.setEmpid(UserManager.getUserCode());
									posOrderSub.setLogdate(new Date());
									posOrderSub.setSaleid(posMaster.getSaleid());
									posOrderSub.setTableno(tableNo);
									posOrderSub.setSiteno("");
									posOrderSub.setCook("");
									posOrderSub.setRemark("");
									posOrderSub.setPrinter("");
									posOrderSub.setEmpid1(posMaster.getEmpid());
									posOrderSub.setEmpid2("");
									posOrderSub.setEmpid3("");
									posOrderDao.save(posOrderSub);
									
									posMaster.setLastnum(posMaster.getLastnum()+1);
									posMasterDao.update(posMaster);
								}
							}
						}
					}
				}
			}
			
			
			
			
		}else{
			throw new BizException("账户不存在！",BizExceptionConstant.INVALID_PARAM);
		}
		return null;
	}

	
	private String getRemoteIp(long hotelGroupId, long hotelId){
		PosIp posIp = new PosIp();
		posIp.setHotelGroupId(hotelGroupId);
		posIp.setHotelId(hotelId);
		posIp.setCode("PMS");
		List<PosIp> list = posIpDao.list(posIp);
		if(list != null && list.size()>0){
			PosIp posIpGet = list.get(0);
			return posIpGet.getServerIp();
		}else{
			return "";
		}
	}

	@Override
	public void updateCancleAccount(long hotelGroupId, long hotelId, String id) {
		PosAccount posAccount = new PosAccount();
		posAccount.setHotelGroupId(hotelGroupId);
		posAccount.setHotelId(hotelId);
		posAccount.setId(Long.parseLong(id));
		List<PosAccount> list=posAccountDao.list(posAccount);
		if(list != null && list.size()>0){
			PosAccount posAccountGet = list.get(0);
			posAccountGet.setSta("X");
			posAccountGet.setBillno("X");
			posAccountDao.update(posAccountGet);
			if(posAccountGet.getReason().equalsIgnoreCase("CDJ")){
				//如果付款方式是冲预付,修改预付定金posPay为有效状态
						//reason = "CDJ"  reasonDesc = posPay.accnt+","+posPay.foliono+","+posPay.credit;
						PosPay posPay = new PosPay();
						posPay.setHotelGroupId(hotelGroupId);
						posPay.setHotelId(hotelId);
						posPay.setAccnt(posAccountGet.getInfo2().split(",")[0]);
						posPay.setSta("O");
						posPay.setFoliono(posAccountGet.getInfo2().split(",")[1]);
						posPay.setCredit(new BigDecimal(posAccountGet.getInfo2().split(",")[2]));
						List<PosPay> payList = posPayDao.list(posPay);
						if(payList!=null && payList.size()>0){
							PosPay posPayChoose = payList.get(0);
							//恢复定金为有效状态   使用:SET  trand = 'T',taccnt = arg_accnt ,tshift = arg_shift,sta= 'O' ,tempid = arg_user, tbdate = var_date,dtl_accnt = arg_accnt 
							posPayChoose.setSta("I");
							posPayChoose.setTrand("");
							posPayChoose.setTaccnt("");
							posPayChoose.setTshift("");
							posPayChoose.setTempid("");
							posPayChoose.setReason("CX");
							posPayChoose.setTbdate(UserManager.getBizDate());
							posPayChoose.setDtlAccnt(posAccountGet.getAccnt());
							posPayDao.update(posPayChoose);
						}
						//插入同步信息表 订单需要同步
						PosAccntSync ls = new PosAccntSync();
						ls.setHotelGroupId(hotelGroupId);
						ls.setHotelId(hotelId);
						ls.setAccnt(posAccountGet.getAccnt());
						ls.setResAccnt(posPay.getAccnt());
						ls.setEntityName("com.greencloud.entity.PosMaster");
						ls.setType("UPDATE");
						ls.setIsHalt("F");
						ls.setIsSync("F");
						ls.setCreateUser(UserManager.getUserCode());
						ls.setModifyUser(UserManager.getUserCode());								
						posAccntSyncDao.save(ls);	
					
			}
			
			
			PosMaster posMaster = this.getPosMasterByAccnt(hotelGroupId, hotelId,posAccountGet.getAccnt());
			
			if(posMaster==null){
				throw new BizException("账户不存在！",BizExceptionConstant.INVALID_PARAM);
			}
			
			CodeTransaction codeTransaction = this.getCodeTransactionByCode(hotelGroupId, hotelId, posAccountGet.getPaycode());
			
			
			String aliCode="";
			String wxCode="";
			SysOption sysOption1 =  sysOptionService.findSysOptionByCatalogItem("scanpay", "alipay_pay_code", hotelGroupId, hotelId);
			SysOption sysOption2 =  sysOptionService.findSysOptionByCatalogItem("scanpay", "wx_pay_code", hotelGroupId, hotelId);
			
			if(sysOption1 != null && sysOption1.getSetValue() != null){
				aliCode=sysOption1.getSetValue();
			}
			
			if(sysOption2 != null && sysOption2.getSetValue() != null){
				wxCode=sysOption2.getSetValue();
			}
			
			if(codeTransaction.getCode().equals(aliCode)  || codeTransaction.getCode().equals(wxCode)){
					String bdesc="";
			    	String scanChannel= "";
					if(codeTransaction.getCode().equals(aliCode) ){
						scanChannel="ALIPAY";
						bdesc = "支付宝扫码-"+UserManager.getHotelDescript();
					}
					if(codeTransaction.getCode().equals(wxCode)){
						scanChannel="WEIXIN";
						bdesc = "微信扫码-"+UserManager.getHotelDescript();
					}
			
				String pmsIp0 = getRemoteIp(hotelGroupId,hotelId);
				if(pmsIp0 != null && !pmsIp0.equals("")){
					IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp0, IPmsPosFacadeService.class);
					try {
						String result=service.saveReturnScanAccount(scanChannel, hotelGroupId, hotelId, posAccountGet.getFoliono(), posAccountGet.getOrderno(), posMaster.getId(), posMaster.getAccnt(), posAccountGet.getCredit());
						
						if(result !=null && result.equals("SUCCESS")){
						}else{
							 throw new BizException("请求退款失败...",BizExceptionConstant.INVALID_PARAM);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						 throw new BizException("请求退款失败...",BizExceptionConstant.INVALID_PARAM);
					}
				}else{
					 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
				}
				
		}
			String inerfaceMode = "";
			String pmsPaycode = "";
			SysOption sysOption =  sysOptionService.findSysOptionByCatalogItem("member", "interface_mod", hotelGroupId, hotelId);
			SysOption sysOptionpay =  sysOptionService.findSysOptionByCatalogItem("pos", "paycode_need_reation_pms", hotelGroupId, hotelId);
//			if(sysOption != null && sysOption.getSetValue() != null){
//				inerfaceMode = sysOption.getSetValue();
//			}
			if(sysOptionpay != null && sysOptionpay.getSetValue() != null){
				pmsPaycode = sysOptionpay.getSetValue();
			}
//			if("KAIYUAN".equals(inerfaceMode) && codeTransaction.getCatPosting().equals("RCV")){
			if(codeTransaction.getCatPosting().equals("KYK")){
				String ret = kaiYuanInterfaceService.updateCardConsume(hotelGroupId, hotelId, UserManager.getBizDate(), posAccountGet.getAccnt(), posAccountGet.getCredit(), "SV_REDEMPTION_R_P",posAccountGet.getCardno(),"1",posMaster.getGsts(),UserManager.getHotelCode(),posAccountGet.getAccnt(),posMaster.getPccode());
				if(!"1".equals(ret)){
					throw new BizException("会员引起的删除扣款失败，请检查！",BizExceptionConstant.INVALID_PARAM);
				}
			}else if(codeTransaction != null && codeTransaction.getCatPosting() != null && (codeTransaction.getCatPosting().equals("TF") || codeTransaction.getCatPosting().equals("TA") || codeTransaction.getCatPosting().equals("RCV")) || codeTransaction.getCatPosting().equals("RCP")
						|| (StringUtil.iContains(pmsPaycode,codeTransaction.getCatPosting()))){
			
				String taCodeTrans = this.getTaCode(hotelGroupId, hotelId, posAccountGet.getPccode(), UserManager.getCashier().toString());
				CodeTransaction codeTransactiontaCodeTrans = this.getCodeTransactionByCode(hotelGroupId, hotelId, taCodeTrans);
				if(codeTransactiontaCodeTrans == null){
					throw new BizException("转账费用码不存在，请检查配置！",BizExceptionConstant.INVALID_PARAM);
				}
				
				PosUserDto posUserDto = new PosUserDto();
				posUserDto.setBizDate(UserManager.getBizDate());
				posUserDto.setCashier(UserManager.getCashier().toString());
				posUserDto.setHotelGroupId(hotelGroupId);
				posUserDto.setHotelId(hotelId);
				posUserDto.setPcCode(posAccountGet.getPccode());
				posUserDto.setTaCode(taCodeTrans);
				posUserDto.setUserCode(UserManager.getUserCode());
				
				List<PosPayDto> listPay = new ArrayList<PosPayDto>();
				PosPayDto posPayDto = new PosPayDto();
				posPayDto.setAccnt(Long.parseLong(posAccountGet.getCardno()));
				if(posAccountGet.getInfo2().indexOf("[") >= 0){
					String remarkNew = posAccountGet.getInfo2().substring(posAccountGet.getInfo2().indexOf("[")+1, posAccountGet.getInfo2().indexOf("]"));
					posPayDto.setCardNo(remarkNew);
				}else{
					posPayDto.setCardNo("");
				}
				posPayDto.setCode(codeTransaction.getCode());
				posPayDto.setDescript(codeTransaction.getDescript());
				posPayDto.setPasswd("");
				posPayDto.setPay(BigDecimal.ZERO.subtract(posAccountGet.getCredit()));
				posPayDto.setReceipt(BigDecimal.ZERO);
				posPayDto.setRemark("餐饮撤销");
				posPayDto.setSysOrder(null);
				listPay.add(posPayDto);
				
				String pmsIp = getRemoteIp(hotelGroupId,hotelId);
				if(pmsIp != null && !pmsIp.equals("")){
					IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
					service.updatePosAccntIn(posUserDto, posAccountGet.getAccnt(), posAccountGet.getTableno(), listPay, "餐饮撤销");
				}else{
					 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
				}
				
			
			}
		}
	}

	@Override
	public List<Object> getStdList(long hotelGroupId, long hotelId,String accnt, String tableNo, String type, long id) {
		List<Object> blist=new ArrayList<Object>();
		if(type.equals("1")){
			//order
			PosOrder posOrder = new PosOrder();
			posOrder.setHotelGroupId(hotelGroupId);
			posOrder.setHotelId(hotelId);
			posOrder.setAccnt(accnt);
			posOrder.setTableno(tableNo);
			posOrder.setId(id);
			List<PosOrder> list = posOrderDao.list(posOrder);
			if(list != null && list.size()>0){
				PosOrder posOrderGet = list.get(0);
				
				PosOrder posOrdersub= new PosOrder();
				posOrdersub.setHotelGroupId(hotelGroupId);
				posOrdersub.setHotelId(hotelId);
				posOrdersub.setAccnt(accnt);
				posOrdersub.setTableno(tableNo);
				posOrdersub.setInumber(posOrderGet.getTnumber());
				List<PosOrder> listsub = posOrderDao.list(posOrdersub);
				if(listsub != null && listsub.size()>0){
					blist.addAll(listsub);
				}
				
			}
			
		}else if(type.equals("0")){
			//detail
			PosDetail posDetail= new PosDetail();
			posDetail.setHotelGroupId(hotelGroupId);
			posDetail.setHotelId(hotelId);
			posDetail.setAccnt(accnt);
			posDetail.setTableno(tableNo);
			posDetail.setId(id);
			List<PosDetail> list1 = posDetailDao.list(posDetail);
			if(list1 != null && list1.size()>0){
				PosDetail posDetailGet = list1.get(0);
				
				PosDetail posDetailsub= new PosDetail();
				posDetailsub.setHotelGroupId(hotelGroupId);
				posDetailsub.setHotelId(hotelId);
				posDetailsub.setAccnt(accnt);
				posDetailsub.setTableno(tableNo);
				posDetailsub.setInumber(posDetailGet.getTnumber());
				List<PosDetail> list1sub = posDetailDao.list(posDetailsub);
				if(list1sub != null && list1sub.size()>0){
					blist.addAll(list1sub);
				}
			}
		}
		
		return blist;
	}
	
	
	public PosOrder getPosOrderById(long hotelGroupId, long hotelId, String accnt,String tableNo, long id){
		PosOrder posOrder = new PosOrder();
		posOrder.setHotelGroupId(hotelGroupId);
		posOrder.setHotelId(hotelId);
		posOrder.setAccnt(accnt);
		posOrder.setTableno(tableNo);
		posOrder.setId(id);
		List<PosOrder> list = posOrderDao.list(posOrder);
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public PosDetail getPosDetailById(long hotelGroupId, long hotelId, String accnt,String tableNo, long id){
		PosDetail posDetail = new PosDetail();
		posDetail.setHotelGroupId(hotelGroupId);
		posDetail.setHotelId(hotelId);
		posDetail.setAccnt(accnt);
		posDetail.setTableno(tableNo);
		posDetail.setId(id);
		List<PosDetail> list = posDetailDao.list(posDetail);
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateIn(long hotelGroupId, long hotelId, String accnt,String tableNo, String type, String ids, long mainId) {
		if(type.equals("1")){
			//order
			
			PosOrder orderMain = this.getPosOrderById(hotelGroupId, hotelId, accnt, tableNo, mainId);
			if(orderMain != null){
				String [] idArr=ids.split(",");
				if(idArr != null && idArr.length>0){
					for(int k=0;k<idArr.length;k++){
						PosOrder posOrder = new PosOrder();
						posOrder.setHotelGroupId(hotelGroupId);
						posOrder.setHotelId(hotelId);
						posOrder.setAccnt(accnt);
						posOrder.setTableno(tableNo);
						posOrder.setId(Long.parseLong(idArr[k]));
						List<PosOrder> list = posOrderDao.list(posOrder);
						if(list != null && list.size()>0){
							PosOrder posOrderGet = list.get(0);
						
							posOrderGet.setInumber(orderMain.getTnumber());
							posOrderGet.setAmount(BigDecimal.ZERO);
							String flag=posOrderGet.getFlag();
							if(flag != null){
								String s1=flag.substring(0, 9);
								String s2=flag.substring(10);
								String s3=s1+"1"+s2;
								posOrderGet.setFlag(s3);
							}
							posOrderDao.update(posOrderGet);
						}
					}
				}
					
			}
			
		}else if(type.equals("0")){
			//detail
			
			PosDetail detailMain = this.getPosDetailById(hotelGroupId, hotelId, accnt, tableNo, mainId);
			if(detailMain != null){
				String [] idArr=ids.split(",");
				if(idArr != null && idArr.length>0){
					for(int p=0;p<idArr.length;p++){
						PosDetail posDetail = new PosDetail();
						posDetail.setHotelGroupId(hotelGroupId);
						posDetail.setHotelId(hotelId);
						posDetail.setAccnt(accnt);
						posDetail.setTableno(tableNo);
						posDetail.setId(Long.parseLong(idArr[p]));
						List<PosDetail> list2 = posDetailDao.list(posDetail);
						if(list2 != null && list2.size()>0){
							PosDetail posDetailGet = list2.get(0);
						
							posDetailGet.setInumber(detailMain.getTnumber());
							posDetailGet.setAmount(BigDecimal.ZERO);
							posDetailGet.setSrv(BigDecimal.ZERO);
							posDetailGet.setDsc(BigDecimal.ZERO);
							posDetailGet.setTax(BigDecimal.ZERO);
							String flag=posDetailGet.getFlag();
							if(flag != null ){
								String s1=flag.substring(0, 9);
								String s2=flag.substring(10);
								String s3=s1+"1"+s2;
								posDetailGet.setFlag(s3);
							}
							posDetailDao.update(posDetailGet);
						}
					}
				}
					
			}
			
		}
		
	}

	@Override
	public void updateOut(long hotelGroupId, long hotelId, String accnt,	String tableNo, String type, String ids, long mainId) {
		if(type.equals("1")){
			//order
			
				String [] idArr=ids.split(",");
				if(idArr != null && idArr.length>0){
					for(int k=0;k<idArr.length;k++){
						PosOrder posOrder = new PosOrder();
						posOrder.setHotelGroupId(hotelGroupId);
						posOrder.setHotelId(hotelId);
						posOrder.setAccnt(accnt);
						posOrder.setTableno(tableNo);
						posOrder.setId(Long.parseLong(idArr[k]));
						List<PosOrder> list = posOrderDao.list(posOrder);
						if(list != null && list.size()>0){
							PosOrder posOrderGet = list.get(0);
						
							posOrderGet.setInumber(0);
							posOrderGet.setAmount(posOrderGet.getPrice().multiply(posOrderGet.getNumber()));
							String flag=posOrderGet.getFlag();
							if(flag != null){
								String s1=flag.substring(0, 9);
								String s2=flag.substring(10);
								String s3=s1+"0"+s2;
								posOrderGet.setFlag(s3);
							}
							posOrderDao.update(posOrderGet);
						}
					}
				}
					
			
		}else if(type.equals("0")){
			//detail
			
				String [] idArr=ids.split(",");
				if(idArr != null && idArr.length>0){
					for(int p=0;p<idArr.length;p++){
						PosDetail posDetail = new PosDetail();
						posDetail.setHotelGroupId(hotelGroupId);
						posDetail.setHotelId(hotelId);
						posDetail.setAccnt(accnt);
						posDetail.setTableno(tableNo);
						posDetail.setId(Long.parseLong(idArr[p]));
						List<PosDetail> list2 = posDetailDao.list(posDetail);
						if(list2 != null && list2.size()>0){
							PosDetail posDetailGet = list2.get(0);
						
							posDetailGet.setInumber(0);
							posDetailGet.setAmount(posDetailGet.getPrice().multiply(posDetailGet.getNumber()));
							String flag=posDetailGet.getFlag();
							if(flag != null){
								String s1=flag.substring(0, 9);
								String s2=flag.substring(10);
								String s3=s1+"0"+s2;
								posDetailGet.setFlag(s3);
							}
							posDetailDao.update(posDetailGet);
						}
					}
				}
					
			}
			
		
	}
	
	public String getPcDescByCode(long hotelGroupId, long hotelId,String code){
		PosPccode posPccode = new PosPccode();
		posPccode.setHotelGroupId(hotelGroupId);
		posPccode.setHotelId(hotelId);
		posPccode.setCode(code);
		List<PosPccode> list = posPccodeDao.list(posPccode);
		if(list != null && list.size()>0){
			return list.get(0).getDescript();
		}else{
			return "";
		}
	}
	
	public String getPcDescEnByCode(long hotelGroupId, long hotelId,String code){
		PosPccode posPccode = new PosPccode();
		posPccode.setHotelGroupId(hotelGroupId);
		posPccode.setHotelId(hotelId);
		posPccode.setCode(code);
		List<PosPccode> list = posPccodeDao.list(posPccode);
		if(list != null && list.size()>0){
			return list.get(0).getDescriptEn();
		}else{
			return "";
		}
	}
	
	public String getTableDescByCode(long hotelGroupId, long hotelId,String code){
		PosPccodeTable posPccodeTable = new PosPccodeTable();
		posPccodeTable.setHotelGroupId(hotelGroupId);
		posPccodeTable.setHotelId(hotelId);
		posPccodeTable.setCode(code);
		List<PosPccodeTable> list = posPccodeTableDao.list(posPccodeTable);
		if(list != null && list.size()>0){
			return list.get(0).getDescript();
		}else{
			return "";
		}
	}
	
	public String getTableDescEnByCode(long hotelGroupId, long hotelId,String code){
		PosPccodeTable posPccodeTable = new PosPccodeTable();
		posPccodeTable.setHotelGroupId(hotelGroupId);
		posPccodeTable.setHotelId(hotelId);
		posPccodeTable.setCode(code);
		List<PosPccodeTable> list = posPccodeTableDao.list(posPccodeTable);
		if(list != null && list.size()>0){
			return list.get(0).getDescriptEn();
		}else{
			return "";
		}
	}

	@Override
	public PosBillDto getPosBillDtoByAccnt(long hotelGroupId, long hotelId,String accnt, String tableNo) {
		PosMaster posMaster = this.getPosMasterByAccnt(hotelGroupId, hotelId,accnt);
		if(posMaster != null){
			PosBillDto posBillDto = new PosBillDto();
			posBillDto.setAccnt(posMaster.getAccnt());
			posBillDto.setBillNo(posMaster.getBillno());
			posBillDto.setBizDate(UserManager.getBizDate());
			posBillDto.setCashier(posMaster.getShift());
			posBillDto.setCouser(UserManager.getUserName());
			posBillDto.setNum(String.valueOf(posMaster.getGsts()+posMaster.getChildren()));
			posBillDto.setPccode(posMaster.getPccode());
			posBillDto.setPcDesc(getPcDescByCode(hotelGroupId,hotelId,posMaster.getPccode()));
			posBillDto.setPcDescEn(getPcDescEnByCode(hotelGroupId,hotelId,posMaster.getPccode()));
			posBillDto.setTableNo(posMaster.getTableno());
			posBillDto.setTableDesc(getTableDescByCode(hotelGroupId,hotelId,posMaster.getTableno()));
			posBillDto.setTableDescEn(getTableDescEnByCode(hotelGroupId,hotelId,posMaster.getTableno()));
			
			posBillDto.setLastpnum(String.valueOf(posMaster.getLastpnum()));
			
			posBillDto.setMarket(posMaster.getMarket());
			posBillDto.setSource(posMaster.getSource());
			
			posBillDto.setMarketDesc(getCodeBaseByPcode(hotelGroupId,hotelId,"pos_market",posMaster.getMarket()));
			posBillDto.setSourceDesc(getCodeBaseByPcode(hotelGroupId,hotelId,"pos_source",posMaster.getSource()));
			posBillDto.setTag("0");
			return posBillDto;
		}else{
			PosMasterHistory posMasterHistory = this.getPosMasterHistoryByAccnt(hotelGroupId, hotelId,accnt);
			if(posMasterHistory != null){
				PosBillDto posBillDto2 = new PosBillDto();
				posBillDto2.setAccnt(posMasterHistory.getAccnt());
				posBillDto2.setBillNo(posMasterHistory.getBillno());
				posBillDto2.setBizDate(UserManager.getBizDate());
				posBillDto2.setCashier(posMasterHistory.getShift());
				posBillDto2.setCouser(UserManager.getUserName());
				posBillDto2.setNum(String.valueOf(posMasterHistory.getGsts()+posMasterHistory.getChildren()));
				posBillDto2.setPccode(posMasterHistory.getPccode());
				posBillDto2.setPcDesc(getPcDescByCode(hotelGroupId,hotelId,posMasterHistory.getPccode()));
				posBillDto2.setPcDescEn(getPcDescEnByCode(hotelGroupId,hotelId,posMasterHistory.getPccode()));
				posBillDto2.setTableNo(posMasterHistory.getTableno());
				posBillDto2.setTableDesc(getTableDescByCode(hotelGroupId,hotelId,posMasterHistory.getTableno()));
				posBillDto2.setTableDescEn(getTableDescEnByCode(hotelGroupId,hotelId,posMasterHistory.getTableno()));
				
				posBillDto2.setMarket(posMasterHistory.getMarket());
				posBillDto2.setSource(posMasterHistory.getSource());
				
				posBillDto2.setMarketDesc(getCodeBaseByPcode(hotelGroupId,hotelId,"pos_market",posMasterHistory.getMarket()));
				posBillDto2.setSourceDesc(getCodeBaseByPcode(hotelGroupId,hotelId,"pos_source",posMasterHistory.getSource()));
				posBillDto2.setTag("1");
				return posBillDto2;
			}
		}
		return null;
	}
	
	private String getCodeBaseByPcode(long hotelGroupId, long hotelId, String pcode,String code){
		if(code == null || code.equals("")){
			return "";
		}
		CodeBase cb=new CodeBase();
		cb.setHotelGroupId(hotelGroupId);
		cb.setHotelId(hotelId);
		cb.setParentCode(pcode);
		cb.setCode(code);
		List<CodeBase> list =codeBaseDao.list(cb);
		if(list != null && list.size()>0){
			CodeBase cbGet = list.get(0);
			return cbGet.getDescript();
		}else{
			return "";
		}
	}

	@Override
	public String updateKitchenInputDishcard(String hotelGroupId,String hotelId, String accnt, String id, String type, String station) {
		return posDetailDao.updateKitchenInputDishcard(hotelGroupId, hotelId, accnt, id, type, station);
	}

	@Override
	public void updateZhuanSetail(long hotelGroupId,long hotelId,PosMaster posMaster, String tableNo,String type, long id, String num, String zaccnt, String ztable) {
		PosMaster zmaster = this.getPosMasterByAccnt(hotelGroupId, hotelId, zaccnt);
		if(zmaster == null){
			 throw new BizException("所选主单不存在！",BizExceptionConstant.INVALID_PARAM);
		}
		
		if(posMaster.getAccnt().equals(zmaster.getAccnt()) && tableNo.equals(ztable)){
			 throw new BizException("不能转给自己！",BizExceptionConstant.INVALID_PARAM);
		}
		
		PosDetail posDetail= new PosDetail();
		posDetail.setHotelGroupId(hotelGroupId);
		posDetail.setHotelId(hotelId);
		posDetail.setAccnt(posMaster.getAccnt());
		posDetail.setTableno(tableNo);
		posDetail.setId(id);
		List<PosDetail> list1 = posDetailDao.list(posDetail);
		if(list1 != null && list1.size()>0){
			PosDetail posDetailGet = list1.get(0);
			BigDecimal  znum = new BigDecimal(num);
			int tnumdetail =posDetailGet.getTnumber();
			
			String flag=posDetailGet.getFlag();
			String taoCaiflag=flag.substring(0, 1);
			if(taoCaiflag.equals("1")){
				//套菜只能全转
				if(posDetailGet.getNumber().compareTo(znum)  != 0){
					 throw new BizException("套菜不能部分转菜！",BizExceptionConstant.INVALID_PARAM);
				}
			}
			
			
		
			if(posDetailGet.getNumber().compareTo(znum) == 0){
				//数量相等
				posDetailGet.setPcid(UserManager.getWorkStationId().toString());
				posDetailGet.setAccnt(zmaster.getAccnt());
				posDetailGet.setTableno(zmaster.getTableno());
				posDetailGet.setInumber(0);
				posDetailGet.setTnumber(zmaster.getLastnum()+1);
				posDetailGet.setAnumber(0);
				posDetailGet.setMnumber(0);
				posDetailDao.update(posDetailGet);
				
				zmaster.setLastnum(zmaster.getLastnum()+1);
				posMasterDao.update(zmaster);
				
				
				
				//如果是套菜  明细菜也必须转
				if(taoCaiflag.equals("1")){
					PosDetail posDetailsub= new PosDetail();
					posDetailsub.setHotelGroupId(hotelGroupId);
					posDetailsub.setHotelId(hotelId);
					posDetailsub.setAccnt(posMaster.getAccnt());
					posDetailsub.setTableno(tableNo);
					posDetailsub.setInumber(tnumdetail);
					List<PosDetail> list1sub = posDetailDao.list(posDetailsub);
					if(list1sub != null && list1sub.size()>0){
						
						for(Iterator<PosDetail> s =list1sub.iterator();s.hasNext(); ){
							PosDetail posDetailSubGet = s.next();
							
							
							
							posDetailSubGet.setPcid(UserManager.getWorkStationId().toString());
							posDetailSubGet.setAccnt(zmaster.getAccnt());
							posDetailSubGet.setTableno(zmaster.getTableno());
							posDetailSubGet.setInumber(posDetailGet.getTnumber());
							posDetailSubGet.setTnumber(zmaster.getLastnum()+1);
							posDetailSubGet.setAnumber(0);
							posDetailSubGet.setMnumber(0);
							posDetailDao.update(posDetailSubGet);
							
							zmaster.setLastnum(zmaster.getLastnum()+1);
							posMasterDao.update(zmaster);
						}
					}
				}
			
			}else if(posDetailGet.getNumber().compareTo(znum) == 1){
				//所转数量小
				
				//原始数量
				BigDecimal  onum = posDetailGet.getNumber();
				
				//znum   所转数量
				
				//剩下的数量
				BigDecimal  anum = posDetailGet.getNumber().subtract(znum);
				BigDecimal  aamount = posDetailGet.getAmount().multiply(anum).divide(onum);
				BigDecimal  zamount = posDetailGet.getAmount().subtract(aamount);
				
				posDetailGet.setNumber(anum);
				posDetailGet.setAmount(aamount);
				posDetailDao.update(posDetailGet);
				
				
				
				
				
				
				
				
				
				
				
				//处理新到方
				PosDetail posDetailNew = posDetailGet.cloneEx();
				posDetailNew.setPcid(UserManager.getWorkStationId().toString());
				posDetailNew.setAccnt(zmaster.getAccnt());
				posDetailNew.setTableno(zmaster.getTableno());
				posDetailNew.setInumber(0);
				posDetailNew.setTnumber(zmaster.getLastnum()+1);
				posDetailNew.setAnumber(0);
				posDetailNew.setMnumber(0);
				posDetailNew.setNumber(znum);
				posDetailNew.setAmount(zamount);
				posDetailDao.save(posDetailNew);
				
				zmaster.setLastnum(zmaster.getLastnum()+1);
				posMasterDao.update(zmaster);
			}else if(posDetailGet.getNumber().compareTo(znum) == 0){
				//所转数量大
				 throw new BizException("数量不足！",BizExceptionConstant.INVALID_PARAM);
			}
		}
	}

	@Override
	public void savePosOrderCopyPlu(long hotelGroupId, long hotelId,
			PosMaster posMasterTarget, String accnt, String tableno, String type)
	{
		PosMaster posMaster = this.getPosMasterByAccnt(hotelGroupId, hotelId,posMasterTarget.getAccnt());
		//从order单里复制
		if(type.equals("1")){
		    PosOrder posOrder = new PosOrder();
		    posOrder.setAccnt(accnt);
		    posOrder.setTableno(tableno);
		    posOrder.setSta("I");
		    List<PosOrder> listBefore = new ArrayList<PosOrder>();
		    listBefore = posOrderDao.list(posOrder);
		    if(listBefore!=null && listBefore.size()>0){
		    	 List<PosOrder> listNew = new ArrayList<PosOrder>();
		    	 for(int i=0 ; i<listBefore.size();i++){
		    		 PosOrder posOrderNew = listBefore.get(i).cloneEx();
		    		 listNew.add(posOrderNew);
		    	 }
		    	 saveCachePosOrder(hotelGroupId, hotelId, posMaster.getAccnt(), posMaster.getTableno(), listNew);
		    }
		}else if(type.equals("2")) {
			PosDetail posDetail = new PosDetail();
			posDetail.setAccnt(accnt);
			posDetail.setTableno(tableno);
			posDetail.setSta("I");
			List<PosDetail> listDeatil = new ArrayList<PosDetail>();
			listDeatil = posDetailDao.list(posDetail);
			if(listDeatil!=null && listDeatil.size()>0){
				 List<PosOrder> listNew2 = new ArrayList<PosOrder>();
				 for(int i=0 ; i<listDeatil.size();i++){
					 PosOrder posOrderSub = new PosOrder();
					 PosDetail detail  = listDeatil.get(i);
						posOrderSub.setHotelGroupId(hotelGroupId);
						posOrderSub.setHotelId(hotelId);
						posOrderSub.setPcid(UserManager.getWorkStationId().toString());
						posOrderSub.setAccnt(posMaster.getAccnt());
						posOrderSub.setInumber(detail.getInumber());
						posOrderSub.setTnumber(detail.getTnumber());
						posOrderSub.setAnumber(detail.getAnumber());
						posOrderSub.setMnumber(detail.getMnumber());
						posOrderSub.setType("1");
						posOrderSub.setOrderno("0");
						posOrderSub.setCondCode(detail.getCondCode());
						posOrderSub.setDescript(detail.getDescript());
						posOrderSub.setDescriptEn(detail.getDescriptEn());
						posOrderSub.setNoteCode(detail.getNoteCode());
						posOrderSub.setSortCode(detail.getSortCode());
						posOrderSub.setPluCode(detail.getCode());
						posOrderSub.setTocode(detail.getTocode());
						posOrderSub.setUnit(detail.getUnit());
						posOrderSub.setNumber(detail.getNumber());
						posOrderSub.setPrice(detail.getPrice());
						posOrderSub.setAmount(detail.getAmount());
						posOrderSub.setSta(detail.getSta());
						posOrderSub.setFlag(detail.getFlag());
						posOrderSub.setFlag1("0000000000");
						posOrderSub.setEmpid(UserManager.getUserCode());
						posOrderSub.setLogdate(new Date());
						posOrderSub.setSaleid("");
						posOrderSub.setTableno(posMaster.getTableno());
						posOrderSub.setSiteno(detail.getSiteno());
						posOrderSub.setCook(detail.getCook());
						posOrderSub.setRemark("");
						posOrderSub.setPrinter("");
						posOrderSub.setEmpid1(posMaster.getEmpid());
						posOrderSub.setEmpid2("");
						posOrderSub.setEmpid3("");
					 listNew2.add(posOrderSub);
		    	 }
				 saveCachePosOrder(hotelGroupId, hotelId, posMaster.getAccnt(), posMaster.getTableno(), listNew2);
			}
		}
		
	}

	@Override
	public String updateCheckOutHotelTransfer(
			PosHotelTransfer hotelTransferData, long hotelGroupId,
			long hotelId, String coi, PosMaster posMaster, String tableNo,
			String accnts, String taCode, BigDecimal pay, String reason,
			String reasonDesc, String cardno, String remark, String balance)
	{
		//首检查付款码是否存在
				CodeTransaction codeTransaction = this.getCodeTransactionByCode(hotelGroupId, hotelId, taCode);
				if(codeTransaction == null){
					throw new BizException("付款码不存在，请检查配置！",BizExceptionConstant.INVALID_PARAM);
				}
				if("0".equals(UserManager.getCashier().toString())){
					throw new BizException("系统班次为0，不允许结账，请退出系统重新登入！",BizExceptionConstant.INVALID_PARAM);
				}
				if("".equals(this.getTaCode(hotelGroupId, hotelId, posMaster.getPccode(), UserManager.getCashier().toString()))){
					throw new BizException("营业点班次对应费用码未定义，不能结账！",BizExceptionConstant.INVALID_PARAM);
				}
				
				//结账前先检查主单状态，当心有其他站点进行了消单或已经结账操作
				PosMaster posMasterSet = new PosMaster();
				posMasterSet.setHotelGroupId(hotelGroupId);
				posMasterSet.setHotelId(hotelId);
				posMasterSet.setAccnt(posMaster.getAccnt());
				List<PosMaster> posMasterSetList = posMasterDao.list(posMasterSet);
				if(posMasterSetList != null && posMasterSetList.size() > 0){			
					if(posMasterSetList.get(0).getSta().equals("X")){
						throw new BizException("该主单状态已经是消单状态，不能再结账", BizExceptionConstant.INVALID_PARAM);
					}
					if(posMasterSetList.get(0).getSta().equals("O")){
						throw new BizException("该主单状态已经处于结账状态，不能再结账", BizExceptionConstant.INVALID_PARAM);
					}
				}
				
				String billNo="";
				//param coi  标志 0 入账并结账，1入账
				
				//入account
				if(pay.compareTo(BigDecimal.ZERO) != 0){
					PosAccount  posAccount = new PosAccount();
					posAccount.setHotelGroupId(hotelGroupId);
					posAccount.setHotelId(hotelId);
					posAccount.setAccnt(posMaster.getAccnt());
					posAccount.setNumber(2);
					posAccount.setInumber(0);
					posAccount.setSubid(0);
					posAccount.setShift(UserManager.getCashier().toString());
					posAccount.setPccode(posMaster.getPccode());
					posAccount.setTableno(tableNo);
					posAccount.setEmpid(UserManager.getUserCode());
					posAccount.setBizDate(UserManager.getBizDate());
					posAccount.setLogdate(new Date());
					posAccount.setPaycode(codeTransaction.getCode());
					posAccount.setDescript(codeTransaction.getDescript());
					posAccount.setDescriptEn(codeTransaction.getDescriptEn());
					posAccount.setAmount(BigDecimal.ZERO);
					posAccount.setCredit(pay);
					posAccount.setBal(BigDecimal.ZERO);
					posAccount.setBillno("");
					posAccount.setFoliono("");
					posAccount.setOrderno("");
					posAccount.setSign("");
					posAccount.setFlag("");
					posAccount.setSta("I");
					posAccount.setReason(reason);
					posAccount.setInfo1(remark+"(转"+hotelTransferData.getTransferHotelDescript()+")");
					posAccount.setInfo2(reasonDesc);
					posAccount.setBank(balance);
					posAccount.setCardno(cardno);
					
					if(codeTransaction.getCatPosting().equals("TOF")){
						posAccount.setDtlAccnt(hotelTransferData.getTransferHotelGroupId()+"/"+hotelTransferData.getTransferHotelId()+"/"+hotelTransferData.getTransactionPmsCode());
					}else if(codeTransaction.getCatPosting().equals("TOA")){
						posAccount.setDtlAccnt(hotelTransferData.getTransferHotelGroupId()+"/"+hotelTransferData.getTransferHotelId()+"/"+hotelTransferData.getTransactionArCode());
					}
					
					posAccountDao.save(posAccount);
					
				}
				
				if(coi != null && coi.equals("0")){
					//结账
					
					
					
					//第一步  插入pos_close
					// 账单编号格式：B+营业点+时间+流水号
					String billno = this.genBillNo("POSBILL", "B"+posMaster.getPccode(), UserManager.getBizDate(), hotelGroupId, hotelId);
//					String billno = this.genBillNo("POSBILL", "B", UserManager.getBizDate(), hotelGroupId, hotelId);
					PosClose posClose = new PosClose();
					posClose.setHotelGroupId(hotelGroupId);
					posClose.setHotelId(hotelId);
					posClose.setAccnt(posMaster.getAccnt());
					posClose.setBillno(billno);
					posClose.setCharge(pay);
					posClose.setPay(pay);
					posClose.setTransType("");
					posClose.setTransAccnt("");
					posClose.setGenBizDate(UserManager.getBizDate());
					posClose.setGenUser(UserManager.getUserCode());
					posClose.setGenCashier(UserManager.getCashier().toString());
					posClose.setGenDatetime(new Date());
					posClose.setDelBizDate(null);
					posClose.setDelCashier(null);
					posClose.setDelDatetime(null);
					posClose.setDelUser(null);
					posClose.setTableno(tableNo);
					posClose.setAuditUser(null);
					posClose.setAuditRemark(null);
					posCloseDao.save(posClose);
					
					//第二步调用结账过程
					int res=posMasterDao.updatePosCheckOut(hotelGroupId, hotelId, UserManager.getBizDate(), UserManager.getCashier().toString(), accnts, billno, UserManager.getUserCode());
					
					if(res==-1){
						throw new BizException("结账调用出错，请检查！",BizExceptionConstant.INVALID_PARAM);
					}
					
					billNo = billno;
				}
				
				if(pay.compareTo(BigDecimal.ZERO) != 0){
					if(codeTransaction.getCatPosting() != null && (codeTransaction.getCatPosting().equals("TF") || codeTransaction.getCatPosting().equals("TA")|| codeTransaction.getCatPosting().equals("TOF") || codeTransaction.getCatPosting().equals("TOA")  || codeTransaction.getCatPosting().equals("RCV")) || codeTransaction.getCatPosting().equals("RCP")){
						/*	该内容不需要 从hotelTransferData里带来的
						 * String taCodeTrans = this.getTaCode(hotelGroupId, hotelId, posMaster.getPccode(), UserManager.getCashier().toString());
							CodeTransaction codeTransactiontaCodeTrans = this.getCodeTransactionByCode(hotelGroupId, hotelId, taCodeTrans);
							if(codeTransactiontaCodeTrans == null){
								throw new BizException("转账费用码不存在，请检查配置！",BizExceptionConstant.INVALID_PARAM);
							}
						*/	
							//转前台
							PosUserDto posUserDto = new PosUserDto();
							posUserDto.setBizDate(UserManager.getBizDate());
							posUserDto.setCashier(UserManager.getCashier().toString());
							posUserDto.setHotelGroupId(hotelTransferData.getTransferHotelGroupId());
							posUserDto.setHotelId(hotelTransferData.getTransferHotelId());
							posUserDto.setPcCode(posMaster.getPccode());
							if(codeTransaction.getCatPosting().equals("TOF")){
								posUserDto.setTaCode(hotelTransferData.getTransactionPmsCode());
							}else if(codeTransaction.getCatPosting().equals("TOA")){
								posUserDto.setTaCode(hotelTransferData.getTransactionArCode());
							}
							if(StringUtil.isEmpty(posUserDto.getTaCode())){
								throw new BizException("跨酒店转账费用码配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
							}
							posUserDto.setUserCode(UserManager.getUserCode());
							List<PosPayDto> listPay = new ArrayList<PosPayDto>();
							PosPayDto posPayDto = new PosPayDto();
							posPayDto.setAccnt(Long.parseLong(cardno));
							posPayDto.setCardNo("");
						    posPayDto.setCode(codeTransaction.getCode());
							posPayDto.setDescript(codeTransaction.getDescript());
							posPayDto.setPasswd("");
							posPayDto.setPay(pay);
							posPayDto.setReceipt(BigDecimal.ZERO);
							posPayDto.setRemark(remark);
							posPayDto.setSysOrder(null);
							listPay.add(posPayDto);
							
							//String pmsIp = getRemoteIp(hotelGroupId,hotelId);
							String pmsIp = hotelTransferData.getTransferServerIp();
							if(pmsIp != null && !pmsIp.equals("")){
								IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
								service.updatePosAccntIn(posUserDto, posMaster.getAccnt(), tableNo, listPay, "酒店:"+UserManager.getHotelDescript()+"(桌号/包厢:"+tableNo+")");
							}else{
								 throw new BizException("跨酒店转账表服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
							}
					}
				}
				return billNo;
	}

	@Override
	public String updateCheckOutMinRenTicket(String ticketsn,
			long hotelGroupId, long hotelId, String coi, PosMaster posMaster,
			String tableNo, String accnts, String taCode, BigDecimal pay,
			String reason, String reasonDesc, String cardno, String remark,
			String balance)
	{
		CodeTransaction codeTransaction = this.getCodeTransactionByCode(hotelGroupId, hotelId, taCode);
		if(codeTransaction == null){
			throw new BizException("付款码不存在，请检查配置！",BizExceptionConstant.INVALID_PARAM);
		}
		if("0".equals(UserManager.getCashier().toString())){
			throw new BizException("系统班次为0，不允许付款，请退出系统重新登入！",BizExceptionConstant.INVALID_PARAM);
		}
		if("".equals(this.getTaCode(hotelGroupId, hotelId, posMaster.getPccode(), UserManager.getCashier().toString()))){
			throw new BizException("营业点班次对应费用码未定义，不能结账！",BizExceptionConstant.INVALID_PARAM);
		}
		String billNo="";
		//入account
		PosAccount  posAccount = new PosAccount();
		//param coi  标志 0 入账并结账，1入账
		if(pay.compareTo(BigDecimal.ZERO) != 0){
		
			posAccount.setHotelGroupId(hotelGroupId);
			posAccount.setHotelId(hotelId);
			posAccount.setAccnt(posMaster.getAccnt());
			posAccount.setNumber(2);
			posAccount.setInumber(0);
			posAccount.setSubid(0);
			posAccount.setShift(UserManager.getCashier().toString());
			posAccount.setPccode(posMaster.getPccode());
			posAccount.setTableno(tableNo);
			posAccount.setEmpid(UserManager.getUserCode());
			posAccount.setBizDate(UserManager.getBizDate());
			posAccount.setLogdate(new Date());
			posAccount.setPaycode(codeTransaction.getCode());
			posAccount.setDescript(codeTransaction.getDescript());
			posAccount.setDescriptEn(codeTransaction.getDescriptEn());
			posAccount.setAmount(BigDecimal.ZERO);
			posAccount.setCredit(pay);
			posAccount.setBal(BigDecimal.ZERO);
			posAccount.setBillno("");
			posAccount.setFoliono("");
			posAccount.setOrderno("");
			posAccount.setSign("");
			posAccount.setFlag("");
			posAccount.setSta("I");
			posAccount.setReason(reason);
			posAccount.setInfo1(remark);
			posAccount.setInfo2(reasonDesc);
			posAccount.setBank(balance);
			posAccount.setCardno(cardno);
			posAccount.setDtlAccnt("");
			posAccountDao.save(posAccount);
	    }
		String result = minRenTicketV2FacadeService.useTickets(posAccount, ticketsn);
		if(StringUtil.isNotEmpty(result)){
			if(coi != null && coi.equals("0")){
				//结账
				//第一步  插入pos_close
				// 账单编号格式：B+营业点+时间+流水号
				String billno = this.genBillNo("POSBILL", "B"+posMaster.getPccode(), UserManager.getBizDate(), hotelGroupId, hotelId);
//				String billno = this.genBillNo("POSBILL", "B", UserManager.getBizDate(), hotelGroupId, hotelId);
				PosClose posClose = new PosClose();
				posClose.setHotelGroupId(hotelGroupId);
				posClose.setHotelId(hotelId);
				posClose.setAccnt(posMaster.getAccnt());
				posClose.setBillno(billno);
				posClose.setCharge(pay);
				posClose.setPay(pay);
				posClose.setTransType("");
				posClose.setTransAccnt("");
				posClose.setGenBizDate(UserManager.getBizDate());
				posClose.setGenUser(UserManager.getUserCode());
				posClose.setGenCashier(UserManager.getCashier().toString());
				posClose.setGenDatetime(new Date());
				posClose.setDelBizDate(null);
				posClose.setDelCashier(null);
				posClose.setDelDatetime(null);
				posClose.setDelUser(null);
				posClose.setTableno(tableNo);
				posClose.setAuditUser(null);
				posClose.setAuditRemark(null);
				posCloseDao.save(posClose);
				//第二步调用结账过程
				int res=posMasterDao.updatePosCheckOut(hotelGroupId, hotelId, UserManager.getBizDate(), UserManager.getCashier().toString(), accnts, billno, UserManager.getUserCode());
				
				if(res==-1){
					throw new BizException("结账调用出错，请检查！",BizExceptionConstant.INVALID_PARAM);
				}
				
				billNo = billno;
			}
		}
		return billNo;
	}
	@Override
	public void updateScanSat(long hotelGroupId, long hotelId,POSInterfaceScanDto pOSInterfaceScanDto) {
		if(pOSInterfaceScanDto != null){
			PosMaster pm=this.getPosMasterByAccnt(hotelGroupId, hotelId, pOSInterfaceScanDto.getRsv_no());
			if(pm != null){
				if(pOSInterfaceScanDto.getRefund_id() != null && !pOSInterfaceScanDto.getRefund_id().equals("")){
					//退款维护
					PosAccount pa=new PosAccount();
					pa.setHotelGroupId(hotelGroupId);
					pa.setHotelId(hotelId);
					pa.setAccnt(pm.getAccnt());
					pa.setFoliono(pOSInterfaceScanDto.getHotel_out_id());
					pa.setOrderno(pOSInterfaceScanDto.getTrade_no());
					List<PosAccount> list = posAccountDao.list(pa);
					if(list != null && list.size()>0){
						PosAccount paGet=list.get(0);
						if(paGet.getSta().equals("X")){
							throw new BizException("账务状态正常，无需维护！",BizExceptionConstant.INVALID_PARAM);
						}else{
							paGet.setSta("X");
							paGet.setBillno("X");
							posAccountDao.update(paGet);
						}
					}else{
						throw new BizException("支付记录不存在！",BizExceptionConstant.INVALID_PARAM);
					}
				}else{
					//入账维护
					//退款维护
					PosAccount pa2=new PosAccount();
					pa2.setHotelGroupId(hotelGroupId);
					pa2.setHotelId(hotelId);
					pa2.setAccnt(pm.getAccnt());
					pa2.setFoliono(pOSInterfaceScanDto.getHotel_out_id());
					pa2.setOrderno(pOSInterfaceScanDto.getTrade_no());
					List<PosAccount> list2 = posAccountDao.list(pa2);
					if(list2 != null && list2.size()>0){
							throw new BizException("账务状态正常，无需维护！",BizExceptionConstant.INVALID_PARAM);
					}else{
						String code="";
						SysOption sysOption=null;
						if(pOSInterfaceScanDto.getScan_channel().equals("WEIXIN")){
							sysOption= sysOptionService.findSysOptionByCatalogItem("scanpay", "wx_pay_code", hotelGroupId, hotelId);
						}else{
							sysOption= sysOptionService.findSysOptionByCatalogItem("scanpay", "alipay_pay_code", hotelGroupId, hotelId);
						}
						
						if(sysOption != null && sysOption.getSetValue() != null){
							code=sysOption.getSetValue();
						}
						
						CodeTransaction codeTransaction=this.getCodeTransactionByCode(hotelGroupId, hotelId, code);
						if(codeTransaction==null){
							throw new BizException("付款码不存在！",BizExceptionConstant.INVALID_PARAM);
						}
						
						PosAccount  posAccount = new PosAccount();
						posAccount.setHotelGroupId(hotelGroupId);
						posAccount.setHotelId(hotelId);
						posAccount.setAccnt(pm.getAccnt());
						posAccount.setNumber(2);
						posAccount.setInumber(0);
						posAccount.setSubid(0);
						posAccount.setShift(UserManager.getCashier().toString());
						posAccount.setPccode(pm.getPccode());
						posAccount.setTableno(pm.getTableno());
						posAccount.setEmpid(UserManager.getUserCode());
						posAccount.setBizDate(UserManager.getBizDate());
						posAccount.setLogdate(new Date());
						posAccount.setPaycode(codeTransaction.getCode());
						posAccount.setDescript(codeTransaction.getDescript());
						posAccount.setDescriptEn(codeTransaction.getDescriptEn());
						posAccount.setAmount(BigDecimal.ZERO);
						posAccount.setCredit(new BigDecimal(pOSInterfaceScanDto.getFee()));
						posAccount.setBal(BigDecimal.ZERO);
						posAccount.setBillno("");
						posAccount.setFoliono(pOSInterfaceScanDto.getHotel_out_id());
						posAccount.setOrderno(pOSInterfaceScanDto.getTrade_no());
						posAccount.setSign("");
						posAccount.setFlag("");
						posAccount.setSta("I");
						posAccount.setReason("");
						posAccount.setInfo1("");
						posAccount.setInfo2("");
						posAccount.setBank("");
						posAccount.setCardno("");
						posAccount.setDtlAccnt("");
						posAccountDao.save(posAccount);
							
					}
					
				}
				
			}else{
				throw new BizException("主单不存在！",BizExceptionConstant.INVALID_PARAM);
			}
		}
	}

	public IKaiYuanInterfaceService getKaiYuanInterfaceService() {
		return kaiYuanInterfaceService;
	}

	public void setKaiYuanInterfaceService(IKaiYuanInterfaceService kaiYuanInterfaceService) {
		this.kaiYuanInterfaceService = kaiYuanInterfaceService;
	}

	@Override
	public long updateRevokePlu(long hotelGroupId,long hotelId,String accnt, long id, String info) {
		long lid = 0;
		 PosMaster posMasterSta = new PosMaster();
		 posMasterSta.setHotelGroupId(hotelGroupId);
		 posMasterSta.setHotelId(hotelId);
		 posMasterSta.setAccnt(accnt);
		 List<PosMaster> posMasterList = posMasterDao.list(posMasterSta);
		 if(posMasterList != null && posMasterList.size() >0){
			if("O".equals(posMasterList.get(0).getSta()) || "X".equals(posMasterList.get(0).getSta())){
				throw new BizException("改主单已经是结账或者无效状态,不能退菜,请刷新餐问题再操作",BizExceptionConstant.INVALID_PARAM);
			}
		 }
		 
		PosDetail posDetail= new PosDetail();
		posDetail.setHotelGroupId(hotelGroupId);
		posDetail.setHotelId(hotelId);
		posDetail.setAccnt(accnt);
		posDetail.setId(id);
		List<PosDetail> list1 = posDetailDao.list(posDetail);
		if(list1 != null && list1.size()>0){
			posDetail = list1.get(0);
			posDetail.setSta("X");
			posDetail.setInfo(info);
			
			posDetailDao.update(posDetail);
			
			PosDetail posDetailNew = new PosDetail();
			posDetailNew = list1.get(0).cloneEx();
			posDetailNew.setId(null);
			posDetailNew.setCreateUser(null);
			posDetailNew.setCreateDatetime(null);
			posDetailNew.setModifyUser(null);
			posDetailNew.setModifyDatetime(null);
			posDetailNew.setInfo(info);
			posDetailNew.setSta("X");
			posDetailNew.setNumber(BigDecimal.ZERO.subtract(posDetailNew.getNumber()));
			posDetailNew.setAmount(BigDecimal.ZERO.subtract(posDetailNew.getAmount()));
			posDetailNew.setDsc(BigDecimal.ZERO.subtract(posDetailNew.getDsc()));
			posDetailNew.setSrv(BigDecimal.ZERO.subtract(posDetailNew.getSrv()));
			posDetailNew.setTax(BigDecimal.ZERO.subtract(posDetailNew.getTax()));
			
			posDetailDao.save(posDetailNew);
			
			lid = posDetailNew.getId();
			
		}
		return lid;
	}
}