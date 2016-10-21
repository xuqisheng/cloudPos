package com.greencloud.service.impl;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.ContextLoader;

import com.aua.dao.IBaseDao;
import com.aua.jdbc.core.JdbcTemplate;
import com.aua.service.impl.BaseServiceImpl;
import com.aua.util.Container;
import com.aua.util.StringHelper;
import com.greencloud.constant.BizExceptionConstant;
import com.greencloud.dao.ICodeTransactionDao;
import com.greencloud.dao.IPosAccntSyncDao;
import com.greencloud.dao.IPosAccountDao;
import com.greencloud.dao.IPosDetailDao;
import com.greencloud.dao.IPosDishcardDao;
import com.greencloud.dao.IPosIpDao;
import com.greencloud.dao.IPosMasterDao;
import com.greencloud.dao.IPosOrderDao;
import com.greencloud.dao.IPosPayDao;
import com.greencloud.dao.IPosPccodeShiftDao;
import com.greencloud.dao.IPosPccodeTableDao;
import com.greencloud.dao.IPosResDao;
import com.greencloud.dao.IPosSubDao;
import com.greencloud.dto.KaiYuanCardPosOldDto;
import com.greencloud.dto.KaiYuanCardPosDto;
import com.greencloud.dto.MemberWithCardDto;
import com.greencloud.dto.POSInterfaceArDto;
import com.greencloud.dto.POSInterfaceArFkDto;
import com.greencloud.dto.POSInterfaceCardDto;
import com.greencloud.dto.POSInterfaceCardFkDto;
import com.greencloud.dto.POSInterfaceFoDto;
import com.greencloud.dto.POSInterfaceFoFkDto;
import com.greencloud.dto.POSInterfaceGuestDto;
import com.greencloud.dto.POSInterfaceGuestFkDto;
import com.greencloud.dto.POSInterfaceScanDto;
import com.greencloud.dto.PosAuditBeginCheckDth;
import com.greencloud.dto.PosAuditProcessCkeckDto;
import com.greencloud.dto.PosLinkDto;
import com.greencloud.dto.PosMasterDto;
import com.greencloud.dto.PosMemberPointDto;
import com.greencloud.dto.PosPayDto;
import com.greencloud.dto.PosUserDto;
import com.greencloud.dto.PosinterfaceFaceArGuestDto;
import com.greencloud.entity.CodeTransaction;
import com.greencloud.entity.PosAccntSync;
import com.greencloud.entity.PosAccount;
import com.greencloud.entity.PosDetail;
import com.greencloud.entity.PosHotelTransfer;
import com.greencloud.entity.PosIp;
import com.greencloud.entity.PosMaster;
import com.greencloud.entity.PosOrder;
import com.greencloud.entity.PosPay;
import com.greencloud.entity.PosPccodeShift;
import com.greencloud.entity.PosRes;
import com.greencloud.entity.SysOption;
import com.greencloud.entity.User;
import com.greencloud.exception.BizException;
import com.greencloud.facade.ICardFacadeService;
import com.greencloud.facade.IMinRenTicketV2Facade;
import com.greencloud.facade.IPmsPosFacadeService;
import com.greencloud.facade.IPosSycFacade;
import com.greencloud.facade.IPosToPosFacade;
import com.greencloud.hessian.HessianProxyFactory;
import com.greencloud.hessian.HessianProxyFactoryBean;
import com.greencloud.service.IKaiYuanInterfaceService;
import com.greencloud.service.IPosAccntSyncService;
import com.greencloud.service.IPosHotelTransferService;
import com.greencloud.service.IPosMasterService;
import com.greencloud.service.IPosSubService;
import com.greencloud.service.ISysOptionService;
import com.greencloud.service.IUserService;
import com.greencloud.util.DateUtil;
import com.greencloud.util.MessageDigestUtil;
import com.greencloud.util.StringUtil;
import com.greencloud.util.UserManager;
public class PosMasterServiceImpl extends BaseServiceImpl implements IPosMasterService
{
   //dao 
   private IPosMasterDao posMasterDao;
   private IPosResDao posResDao;
   private IPosDetailDao posDetailDao;
   private IPosOrderDao posOrderDao;
   private IPosAccountDao posAccountDao;
   private ICodeTransactionDao codeTransactionDao;
   private IPosIpDao posIpDao;
   private IPosPccodeShiftDao posPccodeShiftDao;
   private ISysOptionService sysOptionService;
   private IPosAccntSyncDao posAccntSyncDao;
   private IPosSycFacade posSycFacade;
   private IPosSubService posSubService;
   private IPosAccntSyncService posAccntSyncService;
   private IPosHotelTransferService posHotelTransferService;
   private IUserService userService;
   private IMinRenTicketV2Facade minRenTicketV2FacadeService;
   private IPosPccodeTableDao posPccodeTableDao;
   private IPosDishcardDao posDishcardDao;
   private IKaiYuanInterfaceService kaiYuanInterfaceService;
   private IPosSubDao posSubDao;
   
   private IPosPayDao posPayDao;
//   private ApplicationContext factory;
//   private JdbcTemplate       mysqlJdbcTemplate;
   
//	public JdbcTemplate getMysqlJdbcTemplate() {
//        if (mysqlJdbcTemplate == null) {
//            factory = ContextLoader.getCurrentWebApplicationContext();
//            mysqlJdbcTemplate = (JdbcTemplate) factory.getBean("jdbcTemplate");
//        }
//
//        return mysqlJdbcTemplate;
//	}
   
  public void setPosPayDao(IPosPayDao posPayDao) {
	this.posPayDao = posPayDao;
}
  
  public void setKaiYuanInterfaceService(IKaiYuanInterfaceService kaiYuanInterfaceService) {
		this.kaiYuanInterfaceService = kaiYuanInterfaceService;
	}
  
   public void setMinRenTicketV2FacadeService(
		IMinRenTicketV2Facade minRenTicketV2FacadeService)
{
	this.minRenTicketV2FacadeService = minRenTicketV2FacadeService;
}

public void setPosHotelTransferService(
		IPosHotelTransferService posHotelTransferService)
{
	this.posHotelTransferService = posHotelTransferService;
}

public void setPosSycFacade(IPosSycFacade posSycFacade) {
	this.posSycFacade = posSycFacade;
}

   public void setPosAccntSyncService(IPosAccntSyncService posAccntSyncService) {
	this.posAccntSyncService = posAccntSyncService;
}

public void setSysOptionService(ISysOptionService sysOptionService) {
		this.sysOptionService = sysOptionService;
	}
   
   public void setPosPccodeShiftDao(IPosPccodeShiftDao posPccodeShiftDao) {
 	this.posPccodeShiftDao = posPccodeShiftDao;
 }
   
   
  public void setPosIpDao(IPosIpDao posIpDao) {
	this.posIpDao = posIpDao;
}

public void setCodeTransactionDao(ICodeTransactionDao codeTransactionDao) {
	this.codeTransactionDao = codeTransactionDao;
}

public void setPosAccountDao(IPosAccountDao posAccountDao) {
	this.posAccountDao = posAccountDao;
}

public void setPosDetailDao(IPosDetailDao posDetailDao) {
	this.posDetailDao = posDetailDao;
}

public IPosOrderDao getPosOrderDao() {
	return posOrderDao;
}

public void setPosOrderDao(IPosOrderDao posOrderDao) {
	this.posOrderDao = posOrderDao;
}

public void setPosAccntSyncDao(IPosAccntSyncDao posAccntSyncDao) {
	this.posAccntSyncDao = posAccntSyncDao;
}

public void setPosSubService(IPosSubService posSubService) {
	this.posSubService = posSubService;
}
public void setUserService(IUserService userService) {
	this.userService = userService;
}
	
	private IPosToPosFacade posToPosFacade;
	public void setPosToPosFacade(IPosToPosFacade posToPosFacade) {
		this.posToPosFacade = posToPosFacade;
	}
	
public void setPosPccodeTableDao(IPosPccodeTableDao posPccodeTableDao) {
	this.posPccodeTableDao = posPccodeTableDao;
}

public void setPosDishcardDao(IPosDishcardDao posDishcardDao) {
	this.posDishcardDao = posDishcardDao;
}
public void setPosSubDao(IPosSubDao posSubDao) {
	this.posSubDao = posSubDao;
}
/**
   *save posMaster object  method
   *@param posMaster PosMaster 
   *@throws DataAccessException 
   *@author  
   *@version 1.0
   *@date 2015-04-20 16:24
   */
  public void savePosMaster(PosMaster posMaster) throws DataAccessException{
     	if(log.isDebugEnabled()){
			log.debug("start savePosMaster method");
		}
         posMasterDao.save(posMaster);
     	if(log.isDebugEnabled()){
			log.debug("end savePosMaster method");
		}
  }//end save method
  
public void setPosResDao(IPosResDao posResDao) {
	this.posResDao = posResDao;
}

/**
   *update posMaster method
   *@param posMaster PosMaster
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-04-20 16:24
   */
  public void updatePosMaster(PosMaster posMaster) throws DataAccessException{ 
  	    if(log.isDebugEnabled()){
			log.debug("start updatePosMaster method");
		}
        posMasterDao.update(posMaster);
        if(log.isDebugEnabled()){
			log.debug("end updatePosMaster method");
		}
  }//end update method
  
   /**
   *save or update posMaster object method
   *@param posMaster PosMaster
   *@throws DataAccessException
   *@author  
   *@version 1.0
   *@date 2015-04-20 16:24
   */
  public void saveOrUpdatePosMaster(PosMaster posMaster) throws DataAccessException{//start saveOrUpdate method
      
        if(log.isDebugEnabled()){
			log.debug("start saveOrUpdatePosMaster method");
		}
		
        posMasterDao.saveOrUpdate(posMaster);
        
        if(log.isDebugEnabled()){
			log.debug("end saveOrUpdatePosMaster method");
		}
		
  }//end saveOrUpdate method

  /**
   *query a container obj
   *@param posMaster PosMaster query vo
   *@param firstResult
   *@param maxResults 
   *@throws DataAccessException 
   *@author weihuawon
   *@date 2015-04-20 16:24
   */
  public Container<PosMaster> findPosMaster(PosMaster posMaster,int firstResult ,int maxResults)throws DataAccessException{
        if(log.isDebugEnabled()){
			log.debug("start findPosMaster method");
		}
        Container<PosMaster> container = new Container<PosMaster>();
        List<PosMaster> list = posMasterDao.list(posMaster, firstResult , maxResults);
        container.setResults(list);
        container.setTotalRows(posMasterDao.count(posMaster));
        if(log.isDebugEnabled()){
			log.debug("end findPosMaster method");
		}
       return container;
  }//end find method

  /**
   *count obj amount
   *@param posMaster PosMaster query vo
   *@throws DataAccessException
   *@author 
   *@date 2015-04-20 16:24
   */
  public int countPosMaster(PosMaster posMaster)throws DataAccessException{
    return posMasterDao.count(posMaster);
  }//end count method
  
   /**
   *query a posMaster by id 
   *@param id 
   *@throws DataAccessException 
   *@author 
   *@date 2015-04-20 16:24
   */
  public PosMaster findPosMasterById(Long id)throws DataAccessException{
    PosMaster  posMaster = posMasterDao.load(id);
    return posMaster;
  }//end findPosMasterById method
  
    /**
   *query posMaster collection method
   *@param posMaster PosMaster send query vo 
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-04-20  16:24
   */
  public List<PosMaster> listPosMaster(PosMaster  posMaster)
		throws DataAccessException {
	  List<PosMaster> list = posMasterDao.list(posMaster);
      return list;
  }//end list method
  
   /**
   *query posMaster collection method
   *@param posMaster PosMaster send query vo 
   *@param firstResult 
   *@param maxResults 
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-04-20  16:24
   */
  public List<PosMaster> listPosMaster(PosMaster posMaster,int firstResult ,int maxResults)throws DataAccessException{
	   List<PosMaster> list = posMasterDao.list(posMaster, firstResult ,maxResults);
	   return list;
  }
  
  /**
   * Spring 
   * @param  posMasterDao 
   * @author weihuawon
   * @date 2015年M4月d20�?16:24
   */
  public void setPosMasterDao(IPosMasterDao posMasterDao){ 
     this.posMasterDao = posMasterDao;
  }//end setPosMasterDao method
  
  @Override
  protected IBaseDao getDao(){
    return this.posMasterDao;
  }//end getDao method

@Override
public List<PosMaster> getMasterList(String sql) throws DataAccessException {
	// TODO Auto-generated method stub
	return posMasterDao.getMasterList(sql);
}


/**
 * * 获取新的主单号
	 * @param code 			业务指针类型(RSVNO:酒店预定号；CRSNO:中央预订号  POSMASTER:餐饮开台主单号)
	 * @param type			预订类型前缀（可选参数，传入空串或null将获取酒店预订号）
	 * @param bizDate		营业日期
	 * @param hotelGroupId	集团ID
	 * @param hotelId		酒店ID
	 * @return String
 */
@Override
public String updateNewPosMasterNo(String code,String type,Date bizDate,Long hotelGroupId,Long hotelId){
	
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

@Override
public String saveMaster(PosMaster posMaster,String pcid) {
	// TODO Auto-generated method stub
	SysOption sysOption =  sysOptionService.findSysOptionByCatalogItem("system", "pos_biz_date", posMaster.getHotelGroupId(), posMaster.getHotelId());
//   	单号格式：P+营业点+时间+流水号
		String accnt =  "P"+posMaster.getPccode()+this.updateNewPosMasterNo("POSMASTER", "",DateUtil.parseDateWevFormat(sysOption.getSetValue()), posMaster.getHotelGroupId(), posMaster.getHotelId());
//	String accnt =  "P"+this.updateNewPosMasterNo("POSMASTER", "",DateUtil.parseDateWevFormat(sysOption.getSetValue()), posMaster.getHotelGroupId(), posMaster.getHotelId());
	posMaster.setAccnt(accnt);
	posMaster.setBizDate(DateUtil.parseDateWevFormat(sysOption.getSetValue()));
	if(posMaster.getType1()!= null){
		if(posMaster.getType1().equalsIgnoreCase("WM") ||  posMaster.getType1().equalsIgnoreCase("KC")){
		    posMaster.setTableno(StringUtil.substring(accnt, accnt.length()-4));
		    return posMasterDao.savePosMasterWM(posMaster,pcid);
		}else{
			return "-1,新开单失败!";
		}
	}else{
	   return posMasterDao.savePosMaster(posMaster,pcid);
    }
}

	@Override
	public String updateResNewMaster(PosRes res, PosMaster posMaster, String pcid)
			throws DataAccessException {
		SysOption sysOption =  sysOptionService.findSysOptionByCatalogItem("system", "pos_biz_date", posMaster.getHotelGroupId(), posMaster.getHotelId());
		   //	单号格式：P+营业点+时间+流水号
				String accnt = "P"+posMaster.getPccode()+this.updateNewPosMasterNo("POSMASTER", "",DateUtil.parseDateWevFormat(sysOption.getSetValue()), posMaster.getHotelGroupId(), posMaster.getHotelId());
//		String accnt = "P"+this.updateNewPosMasterNo("POSMASTER", "",DateUtil.parseDateWevFormat(sysOption.getSetValue()), posMaster.getHotelGroupId(), posMaster.getHotelId());
		 posMaster.setAccnt(accnt);
		 posMaster.setBizDate(DateUtil.parseDateWevFormat(sysOption.getSetValue()));
		return posMasterDao.updateResNewMaster(res, posMaster, pcid);
	}
	
	
	@Override
	public PosMasterDto getPosMasterDtoByAccnt(long hotelGroupId, long hotelId,String accnt,String tableNo,String sta) {
		PosMasterDto posMasterDto = new PosMasterDto();
		PosMaster posMaster=new  PosMaster();
		posMaster.setHotelGroupId(hotelGroupId);
		posMaster.setHotelId(hotelId);
		posMaster.setAccnt(accnt);
		List<PosMaster> list = posMasterDao.list(posMaster);
		if(list != null && list.size()>0){
			
			PosMaster posMasterGet = list.get(0);
			posMasterDto.setPosMaster(posMasterGet);
			
			if(posMasterGet.getPcrec() != null && !posMasterGet.getPcrec().equals("")){
				PosMaster posMasterLink=new  PosMaster();
				posMasterLink.setHotelGroupId(hotelGroupId);
				posMasterLink.setHotelId(hotelId);
				posMasterLink.setPcrec(posMasterGet.getPcrec());
				List<PosMaster> listLink = posMasterDao.list(posMasterLink);
				if(listLink != null && listLink.size()>0){
					String accts="";
					String tablenos="";
					
					for(Iterator<PosMaster> i = listLink.iterator();i.hasNext();){
						PosMaster posMasterLinkGet = i.next();
						if(i.hasNext()){
							accts = accts+posMasterLinkGet.getAccnt()+",";
							tablenos = tablenos+posMasterLinkGet.getTableno()+",";	
							if(posMasterLinkGet.getExttableno() != null && !posMasterLinkGet.getExttableno().equals("")){
								tablenos = tablenos+posMasterLinkGet.getExttableno()+",";	
							}
						}else{
							accts = accts+posMasterLinkGet.getAccnt();
							tablenos = tablenos+posMasterLinkGet.getTableno();	
							if(posMasterLinkGet.getExttableno() != null && !posMasterLinkGet.getExttableno().equals("")){
								tablenos = tablenos+","+posMasterLinkGet.getExttableno();	
							}
						}
					}
					
					posMasterDto.setAccnts(accts);
					posMasterDto.setTablenos(tablenos);
				}
			}else{
				posMasterDto.setAccnts(posMasterGet.getAccnt());
				
				
				String pam = posMasterGet.getTableno();
				if(posMasterGet.getExttableno() != null && !posMasterGet.getExttableno().equals("")){
					pam = pam+","+posMasterGet.getExttableno();	
				}
				posMasterDto.setTablenos(pam);
			}
		}
		
		PosDetail posDetail = new PosDetail();
		posDetail.setHotelGroupId(hotelGroupId);
		posDetail.setHotelId(hotelId);
		posDetail.setAccnt(accnt);
		posDetail.setTableno(tableNo);
		if(sta != null && sta.equals("I")){
			posDetail.setSta("I");
		}
		posDetail.setType("1");
		List<PosDetail> list1=posDetailDao.list(posDetail);
		posMasterDto.setList1(list1);
		
		PosOrder posOrder = new PosOrder();
		posOrder.setHotelGroupId(hotelGroupId);
		posOrder.setHotelId(hotelId);
		posOrder.setAccnt(accnt);
		posOrder.setTableno(tableNo);
		posOrder.setSta("I");
		posOrder.setType("1");
		List<PosOrder> list2=posOrderDao.list(posOrder);
		posMasterDto.setList2(list2);
		
		
		
		
		
		return posMasterDto;
	}

	@Override
	public String updateTableNoByAccnt(long hotelGroupId, long hotelId,
			String bizDate, String accnt, String shift, String oldTableNo,
			String newTableNo, String flag, String user) {
		// TODO Auto-generated method stub	
		return posMasterDao.updateTableNoByAccnt(hotelGroupId, hotelId, bizDate, accnt, shift, oldTableNo, newTableNo, flag, user);
	}

	@Override
	public String updateMasterPcrec(long hotelGroupId, long hotelId,
			String oldAccnt, String newAccnt, String flag) {
		String msg = "联单操作失败！" ; 
		// TODO Auto-generated method stub
//		接打出来，进行过联单或撤联，打印行数都变成0
		PosMaster printMasterGet = new PosMaster() ;
		printMasterGet.setHotelGroupId(hotelGroupId);
		printMasterGet.setHotelId(hotelId);
		printMasterGet.setPcrec(oldAccnt);
		List<PosMaster> printList = posMasterDao.list(printMasterGet);
		if(printList != null && printList.size()>0){
			for(Iterator<PosMaster> i =printList.iterator();i.hasNext(); ){
				PosMaster printMasterSet = i.next();
				printMasterSet.setLastpnum(0);
				printMasterSet.setLastrnum(0);
				posMasterDao.update(printMasterSet);
			}
			posMasterDao.deletePosbillAdditionByPcrec(hotelId, hotelGroupId, oldAccnt);
		}
		posMasterDao.deletePosbillAdditionByAccnt(hotelId, hotelGroupId, newAccnt);	
		
		PosMaster oldMaster = new PosMaster() ;
		oldMaster.setHotelGroupId(hotelGroupId);
		oldMaster.setHotelId(hotelId);
		oldMaster.setAccnt(oldAccnt);
		List<PosMaster> list = posMasterDao.list(oldMaster);
		if(list !=null && list.size()>0){
			oldMaster= list.get(0);
		}
		try {
			String [] stringArr= newAccnt.split(",");
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(stringArr));
			stringArr = set.toArray(new String[0]);
			
			for(int i = 0;i<stringArr.length;i++){
				newAccnt = stringArr[i];
			PosMaster newMaster = new PosMaster() ;
			newMaster.setHotelGroupId(hotelGroupId);
			newMaster.setHotelId(hotelId);
			newMaster.setAccnt(newAccnt);
			List<PosMaster> list2 = posMasterDao.list(newMaster);
			if(list !=null && list.size()>0){
				newMaster= list2.get(0);
			}
				// 撤联操作
				if(Integer.parseInt(flag)==1){
					oldMaster.setPcrec(oldAccnt);
					posMasterDao.update(oldMaster);
					newMaster.setPcrec(newAccnt);
					posMasterDao.update(newMaster);
					msg = "撤联操作成功！" ;
				}else if(Integer.parseInt(flag)==2){
					//联单操作
					oldMaster.setPcrec(oldAccnt);
					oldMaster.setLastpnum(0);
					oldMaster.setLastrnum(0);
					System.out.println(oldMaster.getId());
					posMasterDao.update(oldMaster);
					newMaster.setPcrec(oldAccnt);
					newMaster.setLastpnum(0);
					newMaster.setLastrnum(0);
					posMasterDao.update(newMaster);
					msg = "联单操作成功！" ;
				}
				posMasterDao.deletePosbillAdditionByAccnt(hotelId, hotelGroupId, newAccnt);	
			}
		} catch (Exception e) {
			msg = "联单操作失败！" ; 
		}
		return msg;
	}

	
	@Override
	public PosMasterDto updateGetPosMasterDtoForCo1(long hotelGroupId, long hotelId,String accnt, String tableNo,Date bizDate) {
		PosMaster posMaster=new  PosMaster();
		posMaster.setHotelGroupId(hotelGroupId);
		posMaster.setHotelId(hotelId);
		posMaster.setAccnt(accnt);
		List<PosMaster> list = posMasterDao.list(posMaster);
		if(list != null && list.size()>0){
			PosMaster pm=list.get(0);
			String	interfaceMode = "";
			SysOption sysoption = sysOptionService.findSysOptionByCatalogItem("member", "interface_mod", hotelGroupId, hotelId);
			if(sysoption != null && sysoption.getSetValue() != null){
				interfaceMode = sysoption.getSetValue();
			}
			if("KAIYUAN".equals(interfaceMode) && pm.getCardno() != null && !"".equals(pm.getCardno())){
				PosMaster posMasterLink=new  PosMaster();
				posMasterLink.setHotelGroupId(hotelGroupId);
				posMasterLink.setHotelId(hotelId);
				posMasterLink.setPcrec(pm.getPcrec());
				List<PosMaster> listLink = posMasterDao.list(posMasterLink);
				if(listLink != null && listLink.size()>0){
					for(Iterator<PosMaster> i =listLink.iterator();i.hasNext(); ){
						PosMaster posMasterLinkGet = i.next();
						String msg = posMasterDao.updateKaiyuanVipDiscount(hotelGroupId, hotelId, bizDate, posMasterLinkGet.getAccnt(), posMasterLinkGet.getCardno(),posMasterLinkGet.getMode(), posMasterLinkGet.getDsc(), posMasterLinkGet.getSrv(), posMasterLinkGet.getTax());
						if("-1".equals(msg.substring(0, 1))){
							throw new BizException(msg,BizExceptionConstant.INVALID_PARAM);
						}	
					}
				}else{
					String msg = posMasterDao.updateKaiyuanVipDiscount(hotelGroupId, hotelId, bizDate, accnt, pm.getCardno(),pm.getMode(), pm.getDsc(), pm.getSrv(), pm.getTax());
					if("-1".equals(msg.substring(0, 1))){
						throw new BizException(msg,BizExceptionConstant.INVALID_PARAM);
					}	
				}
		
			}else if(pm.getPcrec() != null && !pm.getPcrec().equals("")){
				PosMaster posMasterLink=new  PosMaster();
				posMasterLink.setHotelGroupId(hotelGroupId);
				posMasterLink.setHotelId(hotelId);
				posMasterLink.setPcrec(pm.getPcrec());
				List<PosMaster> listLink = posMasterDao.list(posMasterLink);
				if(listLink != null && listLink.size()>0){
					for(Iterator<PosMaster> i =listLink.iterator();i.hasNext(); ){
						PosMaster posMasterLinkGet = i.next();
						int ret=posMasterDao.updatePosModeCalc(hotelGroupId, hotelId, bizDate, posMasterLinkGet.getAccnt(), posMasterLinkGet.getMode(), posMasterLinkGet.getDsc(), posMasterLinkGet.getSrv(), posMasterLinkGet.getTax());
					}
				}else{
					int ret=posMasterDao.updatePosModeCalc(hotelGroupId, hotelId, bizDate, accnt, pm.getMode(), pm.getDsc(), pm.getSrv(), pm.getTax());
				}
			}else{
				int ret=posMasterDao.updatePosModeCalc(hotelGroupId, hotelId, bizDate, accnt, pm.getMode(), pm.getDsc(), pm.getSrv(), pm.getTax());
			}		
			
		}
		
		return null;
	}
	
	
	private List<PosAccount> getPosPayByAccnt(long hotelGroupId, long hotelId,String accnt){
		
		List<PosAccount> blist = new ArrayList<PosAccount>();
		
		PosAccount posAccount = new PosAccount();
		posAccount.setHotelGroupId(hotelGroupId);
		posAccount.setHotelId(hotelId);
		posAccount.setAccnt(accnt);
		posAccount.setNumber(2);
		List<PosAccount> list= posAccountDao.list(posAccount);
		
		if(list!=null && list.size()>0){
			for(Iterator<PosAccount> i=list.iterator();i.hasNext();){
				PosAccount posAccountGet=i.next(); 
				if(posAccountGet.getSta() != null && !posAccountGet.getSta().equals("X")){
					blist.add(posAccountGet);
				}
			}
		}
		
		return blist;
	}
	
	
	@Override
	public PosMasterDto updateGetPosMasterDtoForCo2(long hotelGroupId, long hotelId,String accnt, String tableNo,Date bizDate) {
		
		PosMasterDto posMasterDto = new PosMasterDto();
		List<PosAccount>  plist=new ArrayList<PosAccount>();
		
		PosMaster posMaster=new  PosMaster();
		posMaster.setHotelGroupId(hotelGroupId);
		posMaster.setHotelId(hotelId);
		posMaster.setAccnt(accnt);
		List<PosMaster> list = posMasterDao.list(posMaster);
		if(list != null && list.size()>0){
			
			PosMaster posMasterGet = list.get(0);
		
			
					if(posMasterGet.getPcrec() != null && !posMasterGet.getPcrec().equals("")){
						PosMaster posMasterLink=new  PosMaster();
						posMasterLink.setHotelGroupId(hotelGroupId);
						posMasterLink.setHotelId(hotelId);
						posMasterLink.setPcrec(posMasterGet.getPcrec());
						List<PosMaster> listLink = posMasterDao.list(posMasterLink);
						if(listLink != null && listLink.size()>0){
							String accts="";
							String tablenos="";
							BigDecimal amount=BigDecimal.ZERO;
							BigDecimal srvamount=BigDecimal.ZERO;
							BigDecimal taxamount=BigDecimal.ZERO;
							BigDecimal dscamount=BigDecimal.ZERO;
							BigDecimal credit=BigDecimal.ZERO;
							
							for(Iterator<PosMaster> i = listLink.iterator();i.hasNext();){
								PosMaster posMasterLinkGet = i.next();
								if(i.hasNext()){
									accts = accts+posMasterLinkGet.getAccnt()+",";
									tablenos = tablenos+posMasterLinkGet.getTableno()+",";	
									if(posMasterLinkGet.getExttableno() != null && !posMasterLinkGet.getExttableno().equals("")){
										tablenos = tablenos+posMasterLinkGet.getExttableno()+",";	
									}
								}else{
									accts = accts+posMasterLinkGet.getAccnt();
									tablenos = tablenos+posMasterLinkGet.getTableno();	
									if(posMasterLinkGet.getExttableno() != null && !posMasterLinkGet.getExttableno().equals("")){
										tablenos = tablenos+","+posMasterLinkGet.getExttableno();	
									}
								}
								
								amount=amount.add(posMasterLinkGet.getAmount());
								srvamount=srvamount.add(posMasterLinkGet.getSrvamount());
								taxamount=taxamount.add(posMasterLinkGet.getTaxamount());
								dscamount=dscamount.add(posMasterLinkGet.getDscamount());
								credit=credit.add(posMasterLinkGet.getCredit());
								
								plist.addAll(this.getPosPayByAccnt(hotelGroupId, hotelId, posMasterLinkGet.getAccnt()));
							}
							
							posMasterDto.setAccnts(accts);
							posMasterDto.setTablenos(tablenos);
							posMasterDto.setAmount(amount);
							posMasterDto.setSrvamount(srvamount);
							posMasterDto.setTaxamount(taxamount);
							posMasterDto.setDscamount(dscamount);
							posMasterDto.setCredit(credit);
							
							
//							posMasterGet.setAmount(amount);
//							posMasterGet.setSrvamount(srvamount);
//							posMasterGet.setTaxamount(taxamount);
//							posMasterGet.setDscamount(dscamount);
//							posMasterGet.setCredit(credit);
						}
					}else{
						posMasterDto.setAccnts(posMasterGet.getAccnt());
					
						posMasterDto.setAmount(posMasterGet.getAmount());
						posMasterDto.setSrvamount(posMasterGet.getSrvamount());
						posMasterDto.setTaxamount(posMasterGet.getTaxamount());
						posMasterDto.setDscamount(posMasterGet.getDscamount());
						posMasterDto.setCredit(posMasterGet.getCredit());
						
						String pam = posMasterGet.getTableno();
						if(posMasterGet.getExttableno() != null && !posMasterGet.getExttableno().equals("")){
							pam = pam+","+posMasterGet.getExttableno();	
						}
						posMasterDto.setTablenos(pam);
						plist.addAll(this.getPosPayByAccnt(hotelGroupId, hotelId, posMasterGet.getAccnt()));
					}
					
					posMasterDto.setList3(plist);
					posMasterDto.setPosMaster(posMasterGet);
		}
		return posMasterDto;
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
	public String updateCheckoutRevoke(Long hotelGroupId, Long hotelId,String bizDate, String billno, String shift,String flag, String user) {
		String interfaceMode = "";
		String pmsPaycode = "";
		SysOption sysOption =  sysOptionService.findSysOptionByCatalogItem("member", "interface_mod", hotelGroupId, hotelId);
		SysOption sysOptionpay =  sysOptionService.findSysOptionByCatalogItem("pos", "paycode_need_reation_pms", hotelGroupId, hotelId);
		if(sysOption != null && sysOption.getSetValue() != null){
			interfaceMode = sysOption.getSetValue();
		}
		if(sysOptionpay != null && sysOptionpay.getSetValue() != null){
			pmsPaycode = sysOptionpay.getSetValue();
		}
		PosMaster posMasterSet = new PosMaster();
		posMasterSet.setHotelGroupId(hotelGroupId);
		posMasterSet.setHotelId(hotelId);
		posMasterSet.setBillno(billno);
		List<PosMaster> posMasterList = posMasterDao.list(posMasterSet);
		
		PosAccount posAccount = new PosAccount();
		posAccount.setHotelGroupId(hotelGroupId);
		posAccount.setHotelId(hotelId);
		posAccount.setNumber(2);
		posAccount.setBillno(billno);
		
		
		List<PosAccount> list = posAccountDao.list(posAccount);
		if(list != null && list.size()>0){
//			PosMaster posMasterSet = new PosMaster();
//			posMasterSet.setHotelGroupId(hotelGroupId);
//			posMasterSet.setHotelId(hotelId);
//			posMasterSet.setAccnt(list.get(0).getAccnt());
//			List<PosMaster> posMasterList = posMasterDao.list(posMasterSet);
			
//			Object obj = posMasterDao.getCardnoByBillNo(hotelId,hotelGroupId,billno);
			
			BigDecimal creditTotal = new BigDecimal(0);
			
			for(Iterator<PosAccount> i = list.iterator();i.hasNext();){
				PosAccount posAccountGet = i.next();
				// 如果是名人票券的付款
				if(posAccountGet.getReason().equalsIgnoreCase("M-T")){
					minRenTicketV2FacadeService.strickTickets(posAccountGet.getId().intValue(), UserManager.getUserCode());
				}else if(posAccountGet.getReason().equalsIgnoreCase("CDJ")){
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
				else{
					CodeTransaction codeTransaction = this.getCodeTransactionByCode(hotelGroupId, hotelId, posAccountGet.getPaycode());
					
					
					PosMaster posMaster = this.findPosMasterByAccnt(hotelGroupId, hotelId, posAccountGet.getAccnt());
					if(posMaster==null){
						throw new BizException("账户不存在！",BizExceptionConstant.INVALID_PARAM);
					}
					
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
					
					
					
					
//					if("KAIYUAN".equals(interfaceMode) && codeTransaction.getCatPosting().equals("RCV")){
//						String menuNo = "";
//						if(posMasterList.size() > 1){
//							menuNo = posMasterList.get(0).getPcrec();
//						}else{
//							menuNo = posMasterList.get(0).getAccnt();
//						}
					if(codeTransaction.getCatPosting().equals("KYK")){
						String ret = this.updateKaiYuanCheckOut(hotelGroupId,hotelId,bizDate,billno,posAccountGet.getCredit(),"SV_REDEMPTION_R_P",posAccountGet.getCardno(),"0",posMasterList.get(0).getGsts(),UserManager.getHotelCode(),posAccountGet.getAccnt(),posAccountGet.getPccode());
						if(!"1".equals(ret)){
							throw new BizException("因会员卡原因撤销结账失败！",BizExceptionConstant.INVALID_PARAM);
						}
					}else if(codeTransaction != null && codeTransaction.getCatPosting() != null && (codeTransaction.getCatPosting().equals("TF") || codeTransaction.getCatPosting().equals("TA") || codeTransaction.getCatPosting().equals("TOF") || codeTransaction.getCatPosting().equals("TOA") || codeTransaction.getCatPosting().equals("RCV")) || codeTransaction.getCatPosting().equals("RCP")
								|| (sysOptionpay != null && StringUtil.iContains(pmsPaycode,codeTransaction.getCatPosting()))){
						String taCodeTrans = this.getTaCode(hotelGroupId, hotelId, posAccountGet.getPccode(), UserManager.getCashier().toString());
						CodeTransaction codeTransactiontaCodeTrans = this.getCodeTransactionByCode(hotelGroupId, hotelId, taCodeTrans);
						if(codeTransactiontaCodeTrans == null){
							throw new BizException("转账费用码不存在，请检查配置！",BizExceptionConstant.INVALID_PARAM);
						}
						String pmsIp = "";
						PosUserDto posUserDto = new PosUserDto();
						/*
						 * 付款码为跨酒店转前台或者转AR
						 */
						if(codeTransaction.getCatPosting().equals("TOF") || codeTransaction.getCatPosting().equals("TOA")){
							String[] transferData = posAccountGet.getDtlAccnt().split("/");
							Long transferHotelGroupId = Long.parseLong(transferData[0]);
							Long transferHotelId = Long.parseLong(transferData[1]);
							taCodeTrans = transferData[2];
							PosHotelTransfer posHotelTransfer = new PosHotelTransfer();
							posHotelTransfer.setHotelGroupId(hotelGroupId);
							posHotelTransfer.setHotelId(hotelId);
							posHotelTransfer.setTransferHotelGroupId(transferHotelGroupId);
							posHotelTransfer.setTransferHotelId(transferHotelId);
							List<PosHotelTransfer> transferList = posHotelTransferService.listPosHotelTransfer(posHotelTransfer);
							if(transferList!=null && transferList.size()>0){
								pmsIp = transferList.get(0).getTransferServerIp();
							}else{
								throw new BizException("跨酒店转账服务地址错误，请检查配置！",BizExceptionConstant.INVALID_PARAM);
							}
							posUserDto.setHotelGroupId(transferHotelGroupId);
							posUserDto.setHotelId(transferHotelId);
						}else{
							pmsIp = this.getRemoteIp(hotelGroupId, hotelId);
							posUserDto.setHotelGroupId(hotelGroupId);
							posUserDto.setHotelId(hotelId);
						}
						posUserDto.setBizDate(UserManager.getBizDate());
						posUserDto.setCashier(UserManager.getCashier().toString());
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
						if(pmsIp != null && !pmsIp.equals("")){
							IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
							service.updatePosAccntIn(posUserDto, posAccountGet.getAccnt(), posAccountGet.getTableno(), listPay, "餐饮撤销");
						}else{
							 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
						}
					}
			    }				
				
				creditTotal = creditTotal.add(posAccountGet.getCredit());
			}
//	开元积分撤销		
			if("KAIYUAN".equals(interfaceMode)){
				for(Iterator<PosMaster> i = posMasterList.iterator();i.hasNext();){
					PosMaster posMasterGet = i.next();
					if(posMasterGet.getCardno() != null && !"".equals(posMasterGet.getCardno())){
						String menuNo = "";
						if(posMasterList.size() > 1){
							menuNo = posMasterGet.getPcrec();
						}else{
							menuNo = posMasterGet.getAccnt();
						}
						String ret = this.updateKaiYuanCheckOut(hotelGroupId,hotelId,bizDate,billno,BigDecimal.ZERO,"SV_REDEMPTION_R",posMasterGet.getCardno(),"0",posMasterGet.getGsts(),UserManager.getHotelCode(),menuNo,posMasterGet.getPccode());
						if(!"1".equals(ret)){
							throw new BizException("因为会员原因撤销结账传消费失败！",BizExceptionConstant.INVALID_PARAM);
						}
						break;
					}
				}
			}
//			积分统计
			if(posMasterList.get(0).getCardno() != null && !"".equals(posMasterList.get(0).getCardno()) && !"KAIYUAN".equals(interfaceMode)){
				String	taCode = "";
				List<PosMemberPointDto> posMemberPointList = new ArrayList<PosMemberPointDto>();
				PosMemberPointDto posMemberPoint = new PosMemberPointDto();
				CodeTransaction CodeTransaction = new CodeTransaction();
				PosUserDto posUserDto = new PosUserDto();
				
				taCode = this.getTaCode(hotelGroupId, hotelId, posMasterList.get(0).getPccode(), UserManager.getCashier().toString());
				
				CodeTransaction = this.getCodeTransactionByCode(hotelGroupId, hotelId, taCode);
				
				posUserDto.setHotelGroupId(hotelGroupId);
				posUserDto.setHotelId(hotelId);
				posUserDto.setBizDate(UserManager.getBizDate());
				posUserDto.setCashier(UserManager.getCashier().toString());
				posUserDto.setPcCode(posMasterList.get(0).getPccode());
				posUserDto.setTaCode(taCode);
				posUserDto.setUserCode(UserManager.getUserCode());
				
				posMemberPoint.setHotelGroupId(hotelGroupId);
				posMemberPoint.setHotelId(hotelId);
				posMemberPoint.setBizDate(UserManager.getBizDate());
				posMemberPoint.setAccnt(list.get(0).getAccnt());
				posMemberPoint.setCardNo(posMasterList.get(0).getCardno());
				posMemberPoint.setMarket(posMasterList.get(0).getMarket());
				posMemberPoint.setSrc(posMasterList.get(0).getSource());
				posMemberPoint.setPosMode(posMasterList.get(0).getMode());
				posMemberPoint.setTableno("桌号/包厢:"+posMasterList.get(0).getTableno());
				posMemberPoint.setbDate(UserManager.getBizDate());
				posMemberPoint.setTaCode(taCode);
				posMemberPoint.setTaDescript(CodeTransaction.getDescript());
				posMemberPoint.setTaDescriptEn(CodeTransaction.getDescriptEn());
				posMemberPoint.setCharge(BigDecimal.ZERO.subtract(creditTotal));
				posMemberPoint.setPay(BigDecimal.ZERO.subtract(creditTotal));
				posMemberPoint.setNumber(new Long(1));
				posMemberPoint.setCashier(UserManager.getCashier());
				posMemberPoint.setRemark("");
				posMemberPoint.setCreateUser(UserManager.getUserCode());
				posMemberPoint.setPayCode(list.get(0).getPaycode());
				
				posMemberPointList.add(posMemberPoint);	
				
				String pmsIp = getRemoteIp(hotelGroupId,hotelId);
				if(pmsIp != null && !pmsIp.equals("")){
					IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
					service.savePosMemberPoint(posUserDto, posMemberPointList);
				}else{
					 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
				}	
			}
		}
		return posMasterDao.updateCheckoutRevoke(hotelGroupId, hotelId, bizDate, billno, shift, flag, user);
	}
	
	@SuppressWarnings("unchecked")
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
	
	

//	@Override
//	public List<POSInterfaceFoDto> getFoList(long hotelGroupId, long hotelId,String key, String rsvClass) {
//		
//		
//		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
//		if(pmsIp != null && !pmsIp.equals("")){
//			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
//			List<POSInterfaceFoDto> list =service.getPosFoFkDto(hotelGroupId, hotelId, key, rsvClass);
//			return list;
//		}else{
//			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
//		}
//		
//		
//	}
	
	@Override
	public List<POSInterfaceFoDto> getFoList(long hotelGroupId, long hotelId,String key, String rsvClass) {
		
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceFoDto> list =service.getPosFoDto(hotelGroupId, hotelId, key, rsvClass);
			if (list != null && list.size()>0){
				return list;
			}
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
		
		
	}

	@Override
	public List<POSInterfaceArDto> getArList(long hotelGroupId, long hotelId,String key) {
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceArDto> list =service.getPosArDto(hotelGroupId, hotelId, key);
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}		
	}
	
	@Override
	public List<PosinterfaceFaceArGuestDto> getArGuestList(long hotelGroupId, long hotelId,long arId, String relationCode) {
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<PosinterfaceFaceArGuestDto> list =service.getPosArGuestDto(hotelGroupId, hotelId, arId,relationCode);
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}		
	}

	@Override
	public List<POSInterfaceCardDto> getCardList(long hotelGroupId,long hotelId, String key) {
		PosUserDto posUserDto = new PosUserDto();
		posUserDto.setBizDate(UserManager.getBizDate());
		posUserDto.setCashier(UserManager.getCashier().toString());
		posUserDto.setHotelGroupId(hotelGroupId);
		posUserDto.setHotelId(hotelId);
		posUserDto.setPcCode("");
		posUserDto.setTaCode("");
		posUserDto.setUserCode(UserManager.getUserCode());
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceCardDto> list =service.listDiscountModeOfVIP(posUserDto, key);
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
		
		
	}

	@Override
	public String updateMasterRevoke(long hotelGroupId, long hotelId,
			String accnt, String shift, String user,String workStation,String reason) {
		// TODO Auto-generated method stub
		PosMaster posMaster = new PosMaster();
		posMaster.setHotelGroupId(hotelGroupId);
		posMaster.setHotelId(hotelId);
		posMaster.setAccnt(accnt);
		List<PosMaster> list = posMasterDao.list(posMaster);
		if(list != null && list.size() >0){
			if(list.get(0).getSta().equals("O")){
				throw new BizException("该单据已经是结账状态，不能消单，要消单请先撤销结账！",BizExceptionConstant.INVALID_PARAM);
			}else{
				PosAccount posAccount = new PosAccount();
				if(list.get(0).getPcrec() != null && !list.get(0).getPcrec().equals("")){
					PosMaster posMasterLink=new  PosMaster();
					posMasterLink.setHotelGroupId(hotelGroupId);
					posMasterLink.setHotelId(hotelId);
					posMasterLink.setPcrec(list.get(0).getPcrec());
					List<PosMaster> listLink = posMasterDao.list(posMasterLink);
					if(listLink != null && listLink.size()>0){
						for(Iterator<PosMaster> i = listLink.iterator();i.hasNext();){
							PosMaster posMasterLinkGet = i.next();
							
							posAccount.setHotelGroupId(hotelGroupId);
							posAccount.setHotelId(hotelId);
							posAccount.setAccnt(posMasterLinkGet.getAccnt());
							posAccount.setNumber(2);
							posAccount.setSta("I");
							List<PosAccount> posAccountlist = posAccountDao.list(posAccount);
							if(posAccountlist != null && posAccountlist.size() > 0){
								throw new BizException("该单含有效付款，要消单先取消付款！",BizExceptionConstant.INVALID_PARAM);
							}
						}
					}
				}else{
					posAccount.setHotelGroupId(hotelGroupId);
					posAccount.setHotelId(hotelId);
					posAccount.setAccnt(accnt);
					posAccount.setNumber(2);
					posAccount.setSta("I");
					List<PosAccount> posAccountlist = posAccountDao.list(posAccount);
					if(posAccountlist != null && posAccountlist.size() > 0){
						throw new BizException("该单含有效付款，要消单先取消付款！",BizExceptionConstant.INVALID_PARAM);
					}
				}
			}
		}else{
			throw new BizException("没有相关的单据信息，请刷新餐位图后再试！",BizExceptionConstant.INVALID_PARAM);
		}	
		return posMasterDao.updateMasterRevoke(hotelGroupId, hotelId, accnt, shift, user, workStation, reason);
	}

	@Override
	public <T> List<T> getPosDetailModeView(long hotelGroupId, long hotelId,String bizDate, String accnts,String flag) {
		return posMasterDao.getPosDetailModeView(hotelGroupId, hotelId, bizDate, accnts,flag);
	}

	  public PosMaster findPosMasterByAccnt(long hotelGroupId, long hotelId, String accnt){
		    PosMaster  posMaster =new PosMaster();
		    posMaster.setHotelGroupId(hotelGroupId);
		    posMaster.setHotelId(hotelId);
		    posMaster.setAccnt(accnt);
		    List<PosMaster> list = posMasterDao.list(posMaster);
		    if(list != null && list.size()>0){
		    	return list.get(0);
		    }else{
		    	return null;
		    }
		    
		  }//end findPosMasterById method
	  
	@Override
	public void updatePosDec(long hotelGroupId, long hotelId, String accnts,String dec) {
		if(accnts != null){
			String []  arr=accnts.split(",");
			if(arr != null && arr.length>0){
				for(int i=0;i<arr.length;i++){
					String accnt=arr[i];
					PosMaster pm = this.findPosMasterByAccnt(hotelGroupId, hotelId, accnt);
					if(pm != null){
						pm.setDsc(BigDecimal.ONE.subtract(new BigDecimal(dec)));
						posMasterDao.update(pm);
					}
				}
			}
		}
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

	private String getRemoteIpMember(long hotelGroupId, long hotelId){
		PosIp posIp = new PosIp();
		posIp.setHotelGroupId(hotelGroupId);
		posIp.setHotelId(hotelId);
		posIp.setCode("MEMBER");
		List<PosIp> list = posIpDao.list(posIp);
		if(list != null && list.size()>0){
			PosIp posIpGet = list.get(0);
			return posIpGet.getServerIp();
		}else{
			return "";
		}
	}
	
	@Override
	public String updateMasterMergerTable(long hotelGroupId, long hotelId,
			String oldAccnt, String newAccnt, String oldTableno,
			String newTbleno, String flag, String user) {
		String msg = "并台失败！" ; 
		//说明是 同一个单子下面的并台
		if(oldAccnt.equalsIgnoreCase(newAccnt)){
			PosAccount posAccount =  new PosAccount();
			posAccount.setHotelGroupId(hotelGroupId);
			posAccount.setHotelId(hotelId);
			posAccount.setAccnt(oldAccnt);
			posAccount.setTableno(oldTableno);
			posAccount.setNumber(1);
			List<PosAccount> list = posAccountDao.list(posAccount);
			if(list !=null && list.size()>0){
				posAccount= list.get(0);
				posAccount.setSta("O");
				posAccount.setModifyUser(user);
				posAccount.setModifyDatetime(new Date());
				posAccountDao.update(posAccount);
			    msg = "并台成功！" ;
			}
		}
		else{
			// 先按照联单处理，然后处理posAccount 资源
		PosMaster oldMaster = new PosMaster() ;
		oldMaster.setHotelGroupId(hotelGroupId);
		oldMaster.setHotelId(hotelId);
		oldMaster.setAccnt(oldAccnt);
		List<PosMaster> list = posMasterDao.list(oldMaster);
		if(list !=null && list.size()>0){
			oldMaster= list.get(0);
		}
		
		PosMaster newMaster = new PosMaster() ;
		newMaster.setHotelGroupId(hotelGroupId);
		newMaster.setHotelId(hotelId);
		newMaster.setAccnt(newAccnt);
		List<PosMaster> list2 = posMasterDao.list(newMaster);
		if(list !=null && list.size()>0){
			newMaster= list2.get(0);
		}
		PosAccount posAccount =  new PosAccount();
		posAccount.setHotelGroupId(hotelGroupId);
		posAccount.setHotelId(hotelId);
		posAccount.setAccnt(oldAccnt);
		posAccount.setTableno(oldTableno);
		posAccount.setNumber(1);
		List<PosAccount> list3 = posAccountDao.list(posAccount);
		if(list3 !=null && list3.size()>0){
			posAccount= list3.get(0);
		}		
		try {
				//联单操作
				oldMaster.setPcrec(oldAccnt);
				System.out.println(oldMaster.getId());
				posMasterDao.update(oldMaster);
				newMaster.setPcrec(oldAccnt);
				posMasterDao.update(newMaster);
				//资源释放
				posAccount.setSta("O");
				posAccount.setModifyUser(user);
				posAccount.setModifyDatetime(new Date());
				posAccountDao.update(posAccount);
			    msg = "并台成功！" ;
					
		} catch (Exception e) {
			msg = "并台操作失败！" ; 
		}	
	}	
		return msg;
	}

	@Override
	public List<POSInterfaceGuestDto> getGuestList(long hotelGroupId,long hotelId, String key) {
		PosUserDto posUserDto = new PosUserDto();
		posUserDto.setBizDate(UserManager.getBizDate());
		posUserDto.setCashier(UserManager.getCashier().toString());
		posUserDto.setHotelGroupId(hotelGroupId);
		posUserDto.setHotelId(hotelId);
		posUserDto.setPcCode("");
		posUserDto.setTaCode("");
		posUserDto.setUserCode(UserManager.getUserCode());
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceGuestDto> list =service.listDiscountModeOfGuest(posUserDto, key);
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
		
	}

	@Override
	public List<POSInterfaceGuestDto> getCompanyList(long hotelGroupId,long hotelId, String key) {
		PosUserDto posUserDto = new PosUserDto();
		posUserDto.setBizDate(UserManager.getBizDate());
		posUserDto.setCashier(UserManager.getCashier().toString());
		posUserDto.setHotelGroupId(hotelGroupId);
		posUserDto.setHotelId(hotelId);
		posUserDto.setPcCode("");
		posUserDto.setTaCode("");
		posUserDto.setUserCode(UserManager.getUserCode());
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceGuestDto> list =service.listDiscountModeOfCompany(posUserDto, key);
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
	}

	@Override
	public void updatePosMode(long hotelGroupId, long hotelId, String accnts,String mode) {
		if(accnts != null){
			String []  arr=accnts.split(",");
			if(arr != null && arr.length>0){
				for(int i=0;i<arr.length;i++){
					String accnt=arr[i];
					PosMaster pm = this.findPosMasterByAccnt(hotelGroupId, hotelId, accnt);
					if(pm != null){
						pm.setMode(mode);
						posMasterDao.update(pm);
					}
				}
			}
		}
	}

	@Override
	public List<PosLinkDto> getPosLinkDtot(long hotelGroupId, long hotelId,String accnt, String tableNo, Date bizDate) {
		List<PosLinkDto> bklist = new ArrayList<PosLinkDto>();
		
		
		PosMaster posMaster=new  PosMaster();
		posMaster.setHotelGroupId(hotelGroupId);
		posMaster.setHotelId(hotelId);
		posMaster.setAccnt(accnt);
		List<PosMaster> list = posMasterDao.list(posMaster);
		if(list != null && list.size()>0){
			PosMaster posMasterGet = list.get(0);
					if(posMasterGet.getPcrec() != null && !posMasterGet.getPcrec().equals("")){
						PosMaster posMasterLink=new  PosMaster();
						posMasterLink.setHotelGroupId(hotelGroupId);
						posMasterLink.setHotelId(hotelId);
						posMasterLink.setPcrec(posMasterGet.getPcrec());
						List<PosMaster> listLink = posMasterDao.list(posMasterLink);
						if(listLink != null && listLink.size()>0){
							for(Iterator<PosMaster> i = listLink.iterator();i.hasNext();){
								PosMaster posMasterLinkGet = i.next();
								PosLinkDto pld=new PosLinkDto();
								pld.setAccnt(posMasterLinkGet.getAccnt());
								pld.setTableNo(posMasterLinkGet.getTableno());
								pld.setDescript(posMasterLinkGet.getTableno()+" [ "+posMasterLinkGet.getSta()+" ]");
								pld.setNumber("0");
								pld.setPrice("0");
								pld.setAmount((posMasterLinkGet.getAmount().subtract(posMasterLinkGet.getCredit())).toString());
								bklist.add(pld);
								
								if(posMasterLinkGet.getExttableno() != null && !posMasterLinkGet.getExttableno().equals("")){
									String [] arr = posMasterLinkGet.getExttableno().split(",");
									if(arr != null && arr.length>0){
										for(int j=0;j<arr.length;j++){
											String etno=arr[j];
											
											PosLinkDto plde=new PosLinkDto();
											plde.setAccnt(posMasterLinkGet.getAccnt());
											plde.setTableNo(etno);
											plde.setDescript(etno+" [ "+posMasterLinkGet.getSta()+" ]");
											plde.setNumber("0");
											plde.setPrice("0");
											plde.setAmount("0");
											bklist.add(plde);
										}
									}
								}
							
							}
						}
					}else{
						
					}
		}
		return bklist;
	}


	@Override
	public void updatePosSuspend(long hotelGroupId, long hotelId, String accnt,
			String shift, String user) {
		// TODO Auto-generated method stub
		posMasterDao.updatePosSuspend(hotelGroupId, hotelId, accnt, shift, user);
	}
	@Override
	public List<PosAuditProcessCkeckDto> updatePosAuditProcessCkeck(long hotelGroupId,
			long hotelId, String biz_date) {
		// TODO Auto-generated method stub
		List<PosAuditProcessCkeckDto> list = new ArrayList<PosAuditProcessCkeckDto>();
		list = posMasterDao.updatePosAuditProcessCkeck(hotelGroupId, hotelId, biz_date);
//		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
//		if(pmsIp != null && !pmsIp.equals("")){
//			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
//			String pmsCredit = service.getPosToPmsCredit(hotelGroupId, hotelId, biz_date);
//			for(Iterator<PosAuditProcessCkeckDto> i = list.iterator();i.hasNext();){
//				PosAuditProcessCkeckDto posAuditProcessCkeckDto = i.next();
//				if(posAuditProcessCkeckDto.getExecType().equalsIgnoreCase("PmsCredit")){
//					String[] aArray = pmsCredit.split(",");
//					BigDecimal credit = new BigDecimal(0);
//					 for(int j = 0; j < aArray.length; j++){ 
//						 credit = credit.add(new BigDecimal(aArray[j]));
//					    }
//					posAuditProcessCkeckDto.setExecValue(credit.toString());
//				}
//			}
//			return list;
//		}else{
//			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
//		}
		return list;
	}

	@Override
	public <T> List<T> getTableMapTakeAway(String hotelGroupId, String hotelId,
			String date, String shift, String pccode, String status,
			String type, String flag)
	{
		// TODO Auto-generated method stub
		return posMasterDao.getTableMapTakeAway(hotelGroupId, hotelId, date, shift, pccode, status, type, flag);
	}

	@Override
	public String saveSplitPosMasterDto(String type, String accnt,
			String tableNo, List<PosMasterDto> splitPosMasterDto,long hotelGroupId,long hotelId,String user,Long id)
	{
		String result = accnt+",分单成功,请返回餐位图!";
		//先取消原来的主单
		PosMaster posMaster=new  PosMaster();
		posMaster.setHotelGroupId(hotelGroupId);
		posMaster.setHotelId(hotelId);
		posMaster.setAccnt(accnt);
		posMaster.setTableno(tableNo);
		List<PosMaster> list = posMasterDao.list(posMaster);
		if(list != null && list.size()>0){
				PosMaster posMasterGet = list.get(0);
				if (!posMasterGet.getSta().equals("I")){
					result = accnt+",不是'I'状态，不允许使用分单功能!";
					return result;
				}
				PosAccount posAccountPay = new PosAccount();
				posAccountPay.setHotelGroupId(hotelGroupId);
				posAccountPay.setHotelId(hotelId);
				posAccountPay.setAccnt(posMasterGet.getAccnt());
				posAccountPay.setNumber(2);
				posAccountPay.setSta("I");
				List<PosAccount> posAccountlist = posAccountDao.list(posAccountPay);
				if(posAccountlist != null && posAccountlist.size() > 0){
					result = accnt+",该主单含有有效付款，要分单请先取消付款!";
					return result;
				}
				
				posMasterGet.setType2("YD");
				posMasterGet.setSta("X");
				posMasterGet.setInfo(posMasterGet.getInfo()+",分单-原单取消");
				posMasterDao.update(posMasterGet);
				
				PosAccount posAccount = new PosAccount();
				posAccount.setHotelGroupId(hotelGroupId);
				posAccount.setHotelId(hotelId);
				posAccount.setAccnt(accnt);
				posAccount.setTableno(tableNo);
				posAccount.setSta("I");
				posAccount.setNumber(1);
				PosAccount newPosAccount = new PosAccount();
				List<PosAccount> accountList = posAccountDao.list(posAccount);
				if(accountList != null && accountList.size()>0){
					PosAccount posAccountGet = accountList.get(0);
					newPosAccount = posAccountGet.cloneEx();
					posAccountGet.setSta("X");
					posAccountGet.setBillno("YD");
					posAccountDao.update(posAccountGet);
				}
				PosDetail posDetail = new PosDetail();
				posDetail.setHotelGroupId(posMasterGet.getHotelGroupId());
				posDetail.setHotelId(posMasterGet.getHotelId());
				posDetail.setAccnt(accnt);
				posDetail.setTableno(tableNo);
			    posDetail.setSta("I");
				posDetail.setType("1");
				List<PosDetail> list1=posDetailDao.list(posDetail);
				for(int i=0;i<list1.size();i++){
					list1.get(i).setSta("X");
					list1.get(i).setInfo(list1.get(i).getInfo()+",分单-原单取消");
					list1.get(i).setBillno("YD");
					posDetailDao.update(list1.get(i));
				}
				// 分单原单取消后，状态已经发生变化，需要同步到云端
				PosAccntSync ls = new PosAccntSync();
				ls.setHotelGroupId(hotelGroupId);
				ls.setHotelId(hotelId);
				ls.setAccnt(posMasterGet.getAccnt());
				ls.setResAccnt(posMasterGet.getResno());
				ls.setEntityName("com.greencloud.entity.PosMaster");
				ls.setType("FUPDATE");
				ls.setIsHalt("F");
				ls.setIsSync("F");
				ls.setCreateUser(UserManager.getUserCode());
				ls.setModifyUser(UserManager.getUserCode());								
				posAccntSyncDao.saveOrUpdate(ls);		
			// 分单平均分
			SysOption sysOption =  sysOptionService.findSysOptionByCatalogItem("system", "pos_biz_date", posMaster.getHotelGroupId(), posMaster.getHotelId());
			if(type.equalsIgnoreCase("isAverageSplit")){
				for(int j=0;j<splitPosMasterDto.size();j++){
					PosMaster newMaster = splitPosMasterDto.get(j).getPosMaster().cloneEx();
					   //	单号格式：P+营业点+时间+流水号
					String newAccnt =  "P"+newMaster.getPccode()+this.updateNewPosMasterNo("POSMASTER", "",DateUtil.parseDateWevFormat(sysOption.getSetValue()), posMaster.getHotelGroupId(), posMaster.getHotelId());
//					String newAccnt =  "P"+this.updateNewPosMasterNo("POSMASTER", "",DateUtil.parseDateWevFormat(sysOption.getSetValue()), posMaster.getHotelGroupId(), posMaster.getHotelId());
					newMaster.setAccnt(newAccnt);
					newMaster.setLastpnum(0);
					newMaster.setLastrnum(0);
					posMasterDao.save(newMaster);	
					List<PosDetail> detailList = splitPosMasterDto.get(j).getList1();
					for(int i=0;i<detailList.size();i++){
						PosDetail newPosDetail = detailList.get(i).cloneEx();
						newPosDetail.setAccnt(newAccnt);
						newPosDetail.setTableno(newMaster.getTableno());
						posDetailDao.save(newPosDetail);
					}
					PosAccount newPosAccount1 = newPosAccount.cloneEx();
					newPosAccount1.setAccnt(newAccnt);
					newPosAccount1.setTableno(newMaster.getTableno());
					posAccountDao.save(newPosAccount1);
					
					// 分单原单取消后，状态已经发生变化，需要同步到云端
					PosAccntSync lsa = new PosAccntSync();
					lsa.setHotelGroupId(hotelGroupId);
					lsa.setHotelId(hotelId);
					lsa.setAccnt(newMaster.getAccnt());
					lsa.setResAccnt(newMaster.getResno());
					lsa.setEntityName("com.greencloud.entity.PosMaster");
					lsa.setType("FADD");
					lsa.setIsHalt("F");
					lsa.setIsSync("F");
					lsa.setCreateUser(UserManager.getUserCode());
					lsa.setModifyUser(UserManager.getUserCode());								
					posAccntSyncDao.saveOrUpdate(lsa);	
				}
			}else if(type.equalsIgnoreCase("notAverageSplit")){
				for(int j=0;j<splitPosMasterDto.size();j++){
					PosMaster newMaster = splitPosMasterDto.get(j).getPosMaster().cloneEx();
					   //	单号格式：P+营业点+时间+流水号
					String newAccnt =  "P"+newMaster.getPccode()+this.updateNewPosMasterNo("POSMASTER", "",DateUtil.parseDateWevFormat(sysOption.getSetValue()), posMaster.getHotelGroupId(), posMaster.getHotelId());
//					String newAccnt =  "P"+this.updateNewPosMasterNo("POSMASTER", "",DateUtil.parseDateWevFormat(sysOption.getSetValue()), posMaster.getHotelGroupId(), posMaster.getHotelId());
					newMaster.setAccnt(newAccnt);
					newMaster.setLastpnum(0);
					newMaster.setLastrnum(0);
					posMasterDao.save(newMaster);
					List<PosDetail> detailList2 = splitPosMasterDto.get(j).getList1();
					for(int i=0;i<detailList2.size();i++){
						PosDetail newPosDetail = detailList2.get(i).cloneEx();
						newPosDetail.setAccnt(newAccnt);
						newPosDetail.setTableno(newMaster.getTableno());
						posDetailDao.save(newPosDetail);
					}
					PosAccount newPosAccount1 = newPosAccount.cloneEx();
					newPosAccount1.setAccnt(newAccnt);
					newPosAccount1.setTableno(newMaster.getTableno());
					posAccountDao.save(newPosAccount1);
					
					// 分单原单取消后，状态已经发生变化，需要同步到云端
					PosAccntSync lsna = new PosAccntSync();
					lsna.setHotelGroupId(hotelGroupId);
					lsna.setHotelId(hotelId);
					lsna.setAccnt(newMaster.getAccnt());
					lsna.setResAccnt(newMaster.getResno());
					lsna.setEntityName("com.greencloud.entity.PosMaster");
					lsna.setType("FADD");
					lsna.setIsHalt("F");
					lsna.setIsSync("F");
					lsna.setCreateUser(UserManager.getUserCode());
					lsna.setModifyUser(UserManager.getUserCode());								
					posAccntSyncDao.saveOrUpdate(lsna);	
				}
			}
		}
		return result;
	}

	@Override
	public MemberWithCardDto findPersonWithCardByCardNo(long hotelGroupId,
			long hotelId, String cardNo)
	{
		String memberIp = getRemoteIpMember(hotelGroupId,0);
		if(memberIp != null && !memberIp.equals("")){
			ICardFacadeService service  = this.findInterfaceWithIp(memberIp, ICardFacadeService.class);
			UserManager.setHotelGroupId((long) 1);
			MemberWithCardDto memberWithCardDto = service.findPersonWithCardByCardNo(cardNo);
			return memberWithCardDto;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
	}

	@Override
	public String findMrAccountByCardNo(long hotelGroupId, long hotelId,
			String accnt)
	{
		String memberIp = getRemoteIpMember(hotelGroupId,0);
		if(memberIp != null && !memberIp.equals("")){
			ICardFacadeService service  = this.findInterfaceWithIp(memberIp, ICardFacadeService.class);
			UserManager.setHotelGroupId((long) 1);
			String accntNew = service.findMrAccountByCardNo(accnt);
			return accntNew;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
	}
	
//  添加分库版的协议单位、会员、宾客查询
	@Override
	public List<POSInterfaceGuestFkDto> getCompanyFkList(long hotelGroupId,long hotelId, String key) {
		PosUserDto posUserDto = new PosUserDto();
		posUserDto.setBizDate(UserManager.getBizDate());
		posUserDto.setCashier(UserManager.getCashier().toString());
		posUserDto.setHotelGroupId(hotelGroupId);
		posUserDto.setHotelId(hotelId);
		posUserDto.setPcCode("");
		posUserDto.setTaCode("");
		posUserDto.setUserCode(UserManager.getUserCode());
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceGuestFkDto> list =service.listDiscountModeOfCompanyFk(posUserDto, key);
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
	}
	
	@Override
	public List<POSInterfaceCardFkDto> getCardFkList(long hotelGroupId,long hotelId, String key) {
		PosUserDto posUserDto = new PosUserDto();
		posUserDto.setBizDate(UserManager.getBizDate());
		posUserDto.setCashier(UserManager.getCashier().toString());
		posUserDto.setHotelGroupId(hotelGroupId);
		posUserDto.setHotelId(hotelId);
		posUserDto.setPcCode("");
		posUserDto.setTaCode("");
		posUserDto.setUserCode(UserManager.getUserCode());
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		String interfaceMode = "";
		SysOption sysOption =  sysOptionService.findSysOptionByCatalogItem("member", "interface_mod", hotelGroupId, hotelId);
		if(sysOption != null && sysOption.getSetValue() != null){
			interfaceMode = sysOption.getSetValue();
		}
		if("KAIYUAN".equals(interfaceMode)){
			if(pmsIp != null && !pmsIp.equals("")){
				IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
				KaiYuanCardPosDto kaiYuanCard =service.listKaiYuanDiscountModeOfVIP(posUserDto, key);
				List<POSInterfaceCardFkDto> list = kaiYuanCardInfoChange(kaiYuanCard);				
				return list;
			}else{
				 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
			}		
		}else{
			if(pmsIp != null && !pmsIp.equals("")){
				IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
				List<POSInterfaceCardFkDto> list =service.listDiscountModeOfVIPFk(posUserDto, key);
				return list;
			}else{
				 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
			}
		}		
	}
	
	private List<POSInterfaceCardFkDto> kaiYuanCardInfoChange(KaiYuanCardPosDto kaiYuanCard) {
		List<POSInterfaceCardFkDto> list = new ArrayList<POSInterfaceCardFkDto>();
		POSInterfaceCardFkDto posCardFkDto = new POSInterfaceCardFkDto();
		
		String	remark = "";
		
		posCardFkDto.setCardNo(kaiYuanCard.getSvan());
		posCardFkDto.setCardDescript(kaiYuanCard.getAccountName());
		posCardFkDto.setCardType(kaiYuanCard.getCardType());
		posCardFkDto.setCardTypeDes(kaiYuanCard.getCardType());
		posCardFkDto.setCardLevel(kaiYuanCard.getCardLevelCode());
		posCardFkDto.setCardLevelDes(kaiYuanCard.getCardLevelName());
		posCardFkDto.setAmount(kaiYuanCard.getAccountBalance());
		posCardFkDto.setPointBalance(kaiYuanCard.getPointsBalance());
		posCardFkDto.setFoodDiscount(kaiYuanCard.getDiscount().getFoodDiscount());
		posCardFkDto.setBeveDiscount(kaiYuanCard.getDiscount().getBeveDiscount());
		posCardFkDto.setMiscDiscount(kaiYuanCard.getDiscount().getMiscDiscount());
		posCardFkDto.setDiscount1(kaiYuanCard.getDiscount().getDiscount1());
		posCardFkDto.setDiscount2(kaiYuanCard.getDiscount().getDiscount2());			
		remark = "食品折扣:" + kaiYuanCard.getDiscount().getFoodDiscount().toString() + ";酒水折扣:" + kaiYuanCard.getDiscount().getBeveDiscount().toString() + ";其他折扣:"+kaiYuanCard.getDiscount().getMiscDiscount().toString();
		posCardFkDto.setRemark(remark);
		
		list.add(posCardFkDto);
		return list;
	}

	@Override
	public List<POSInterfaceGuestFkDto> getGuestFkList(long hotelGroupId,long hotelId, String key) {
		PosUserDto posUserDto = new PosUserDto();
		posUserDto.setBizDate(UserManager.getBizDate());
		posUserDto.setCashier(UserManager.getCashier().toString());
		posUserDto.setHotelGroupId(hotelGroupId);
		posUserDto.setHotelId(hotelId);
		posUserDto.setPcCode("");
		posUserDto.setTaCode("");
		posUserDto.setUserCode(UserManager.getUserCode());
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceGuestFkDto> list =service.listDiscountModeOfGuestFk(posUserDto, key);
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
	}

	@Override
	public String updatePosAuditBeginCheck(long hotelGroupId, long hotelId, String bizDate) {
		String posRemoteIp = getRemotePosIp(hotelGroupId,hotelId);
		if(posRemoteIp != null && !posRemoteIp.equals("")){
//			记录夜审开始时间
			SysOption sysDate =sysOptionService.findSysOptionByCatalogItem("system", "pos_biz_date", hotelGroupId, hotelId); 
			try{
				posSubDao.updatePosAuditDate(hotelGroupId, hotelId, bizDate, DateUtil.parseDateWevFormat(sysDate.getSetValue()), UserManager.getUserCode(),UserManager.getWorkStationCode());
			}catch (Exception e) {
				throw new BizException("夜审日志插入有误,检查posauditdate索引！",BizExceptionConstant.INVALID_PARAM);
			}
			
			try{
//				getMysqlJdbcTemplate().update("insert pos_audit_date(hotel_group_id,hotel_id,biz_sdate,biz_date,date_begin,date_end,audit_user) values(?,?,?,?,now(),now(),?)",
//						hotelGroupId,hotelId,bizDate,DateUtil.parseDateWevFormat(sysDate.getSetValue()),UserManager.getUserCode());
							
				IPosToPosFacade service = this.findInterfaceWithIp(posRemoteIp, IPosToPosFacade.class);
				List<PosAuditBeginCheckDth> list = service.getPosAuditBeginCheckRomate(hotelGroupId, hotelId, bizDate);
				if(list != null && list.size() >0 ){
					List<PosAuditBeginCheckDth> listLocal = posMasterDao.getPosAuditBeginCheck(hotelGroupId, hotelId, bizDate);
					if(listLocal != null && listLocal.size()>0){
						ArrayList<Object> posAccntSyncList = new ArrayList<Object>();
						for(Iterator<PosAuditBeginCheckDth> i = listLocal.iterator();i.hasNext();){
							Boolean isExist = false;
							PosAuditBeginCheckDth posCheckDataLocal = i.next();	
							for(Iterator<PosAuditBeginCheckDth> j = list.iterator();j.hasNext();){								
								PosAuditBeginCheckDth posCheckDataRomte = j.next();
								if(posCheckDataLocal.getAccnt().equals(posCheckDataRomte.getAccnt()) && posCheckDataLocal.getTYPE().equals(posCheckDataRomte.getTYPE())){
									if(posCheckDataLocal.getSta().equals(posCheckDataRomte.getSta()) && posCheckDataLocal.getCharge().compareTo(posCheckDataRomte.getCharge())==0
											&& posCheckDataLocal.getDetailrows().compareTo(posCheckDataRomte.getDetailrows())==0 && posCheckDataLocal.getCredit().compareTo(posCheckDataRomte.getCredit())==0
											&& posCheckDataLocal.getAccountrows().compareTo(posCheckDataRomte.getAccountrows())==0 && posCheckDataLocal.getResno().equals(posCheckDataRomte.getResno())
											&& posCheckDataLocal.getRessta().equals(posCheckDataRomte.getRessta())){
										j.remove();
										isExist = true;
										break;
									}else{
										PosAccntSync ls = new PosAccntSync();
										ls.setHotelGroupId(hotelGroupId);
										ls.setHotelId(hotelId);
										ls.setAccnt(posCheckDataLocal.getAccnt());
										ls.setResAccnt(posCheckDataLocal.getResno());
										ls.setEntityName("com.greencloud.entity.PosMaster");
										ls.setType("CHECK");
										ls.setIsHalt("F");
										ls.setIsSync("F");
										ls.setCreateUser(UserManager.getUserCode());
										ls.setModifyUser(UserManager.getUserCode());
										
										posAccntSyncList.add(ls);	
										j.remove();	
										isExist = true;
										break;
									}
								}
							}
							if(!isExist){
								PosAccntSync ls = new PosAccntSync();
								ls.setHotelGroupId(hotelGroupId);
								ls.setHotelId(hotelId);
								ls.setAccnt(posCheckDataLocal.getAccnt());
								ls.setResAccnt(posCheckDataLocal.getResno());
								ls.setEntityName("com.greencloud.entity.PosMaster");
								ls.setType("CHECK");
								ls.setIsHalt("F");
								ls.setIsSync("F");
								ls.setCreateUser(UserManager.getUserCode());
								ls.setModifyUser(UserManager.getUserCode());
								
								posAccntSyncList.add(ls);	
							}
						}
						if(posAccntSyncList != null && posAccntSyncList.size() >0){
					     	posSubService.saveCodeCollection(posAccntSyncList);
						}	
					}
				}
			}
			catch (Exception e) {
				throw new BizException("远程获取数据失败，检查网络是否正常",BizExceptionConstant.INVALID_PARAM);
			}
		}else{
//			throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
			return "0";
		}
		return "1";
	}

	private String getRemotePosIp(long hotelGroupId, long hotelId) {
		
		PosIp posIp = new PosIp();
		posIp.setHotelGroupId(hotelGroupId);
		posIp.setHotelId(hotelId);
		posIp.setCode("RPOS");
		List<PosIp> list = posIpDao.list(posIp);
		if(list != null && list.size()>0){
			PosIp posIpGet = list.get(0);
			return posIpGet.getServerIp();
		}else{
			return "";
		}
	}

	@Override
	public List<PosAuditBeginCheckDth> getPosAuditBeginCheckRomate(long hotelGroupId, long hotelId, String bizDate) {
		
		return posMasterDao.getPosAuditBeginCheck(hotelGroupId, hotelId, bizDate);
	}

	@Override
	public List<POSInterfaceFoDto> getFoListHotelTransfer(
			PosHotelTransfer hotelTransferData,String key, String rsvClass)
	{
		String pmsIp = hotelTransferData.getTransferServerIp();
		if( pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceFoDto> list =service.getPosFoDto(hotelTransferData.getTransferHotelGroupId(), hotelTransferData.getTransferHotelId(), key, rsvClass);
			return list;
		}else{
			 throw new BizException("pos_hotel_transfer转账表配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
		
		
	}
	@Override
	public List<POSInterfaceArDto> getArListHotelTransfer(
			PosHotelTransfer hotelTransferData,String key)
	{
		String pmsIp = hotelTransferData.getTransferServerIp();
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceArDto> list =service.getPosArDto(hotelTransferData.getTransferHotelGroupId(), hotelTransferData.getTransferHotelId(), key);
			return list;
		}else{
			 throw new BizException("pos_hotel_transfer转账表配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}	
	}

	@Override
	public void updatePosAccntSyncByPosMasterChanged(long hotelGroupId,long hotelId, String accnt) {
		posAccntSyncDao.updateByInsterSql(hotelGroupId, hotelId, accnt);
	}

	@Override
	public void updateUserPassword(Long userId, String oldPwd, String newPwd) throws DataAccessException {
//		user = UserServiceImpl.findUserById(userId);
		User user = userService.findUserById(userId);
		if(user != null){
			String diPwd = MessageDigestUtil.digestPassword(oldPwd);
			if(!diPwd.equals(user.getPassword())){
				throw new BizException("原来密码错误!",BizExceptionConstant.WRONG_PASSWORD);
			}
			user.setPassword(MessageDigestUtil.digestPassword(newPwd));
			
			String pmsIp = getRemoteIp(UserManager.getHotelGroupId(),UserManager.getHotelId());
			if(pmsIp != null && !pmsIp.equals("")){
				try {
					IPmsPosFacadeService server = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
					server.updateUserPassword(userId, oldPwd, newPwd);
//					server.updateUserPassword(Long.valueOf(2875), oldPwd, newPwd);
				}
				catch (Exception e) {
					throw new BizException("远程密码修改失败",BizExceptionConstant.WRONG_PASSWORD);
				}
				
				userService.updateUser(user);
			}else{
				throw new BizException("远程服务器配置地址有误，请检查",BizExceptionConstant.WRONG_PASSWORD);
//				throw new BizException("远程服务器配置地址有误，请检查",BizExceptionConstant.INVALID_PARAM);
			}
			
		}else{
			throw new BizException(BizExceptionConstant.INVALID_PARAM);
		}	
	}

	@Override
	public PosMaster getPosMaterByAccnt(long hotelGroupId,long hotelId,String accnt) {
		PosMaster posMasterGet = new PosMaster();
		posMasterGet.setHotelGroupId(hotelGroupId);
		posMasterGet.setHotelId(hotelId);
		posMasterGet.setAccnt(accnt);
		
		List<PosMaster> list = posMasterDao.list(posMasterGet);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;		
		}		
	}


	@Override
	public List<POSInterfaceFoFkDto> getFoFkList(long hotelGroupId, long hotelId,String key, String rsvClass) {
		
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceFoFkDto> list =service.getPosFoFkDto(hotelGroupId, hotelId, key, rsvClass);
			if (list != null && list.size()>0){
				return list;
			}
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
		
		
	}
	
	@Override
	public List<POSInterfaceArFkDto> getArFkList(long hotelGroupId, long hotelId,String key) {
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceArFkDto> list =service.getPosArFkDto(hotelGroupId, hotelId, key);
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}		
	}

	
	
	private boolean isDo(long hotelGroupId,long hotelId,String  accnt,String outId,String tradeNo,String sta){
		PosAccount pa=new PosAccount();
		pa.setHotelGroupId(hotelGroupId);
		pa.setHotelId(hotelId);
		pa.setAccnt(accnt);
		pa.setFoliono(outId);
		pa.setOrderno(tradeNo);
		
		if(sta.equals("X")){
			pa.setSta("X");
		}
		
		List<PosAccount> list = posAccountDao.list(pa);
		if(list != null && list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	@Override
	public List<POSInterfaceScanDto> getScanList(long hotelGroupId,long hotelId, String key) {
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceScanDto> list =service.getScanList(hotelGroupId, hotelId, key);
			if (list != null && list.size()>0){
				for(Iterator<POSInterfaceScanDto> i=list.iterator();i.hasNext();){
					POSInterfaceScanDto posInterfaceScanDto=i.next();
					String sta="I";
					if(posInterfaceScanDto.getRefund_id() != null && !posInterfaceScanDto.getRefund_id().equals("")){
						sta="X";
					}
					
					
					if(isDo(hotelGroupId, hotelId, posInterfaceScanDto.getRsv_no(), posInterfaceScanDto.getHotel_out_id(), posInterfaceScanDto.getTrade_no(), sta)){
						posInterfaceScanDto.setAccount_status("SUCCESS");
					}else{
						posInterfaceScanDto.setAccount_status("FAIL");
					}
					
					
				}
				return list;
			}
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
		
	}

	@Override
	public List<POSInterfaceFoFkDto> getFoListHotelTransferFk(PosHotelTransfer hotelTransferData, String key, String rsvClass) {
		String pmsIp = hotelTransferData.getTransferServerIp();
		if( pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceFoFkDto> list =service.getPosFoFkDto(hotelTransferData.getTransferHotelGroupId(), hotelTransferData.getTransferHotelId(), key, rsvClass);
			return list;
		}else{
			 throw new BizException("pos_hotel_transfer转账表配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
	}

	@Override
	public List<POSInterfaceArFkDto> getArListHotelTransferFk(PosHotelTransfer hotelTransferData, String key) {
		String pmsIp = hotelTransferData.getTransferServerIp();
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			List<POSInterfaceArFkDto> list =service.getPosArFkDto(hotelTransferData.getTransferHotelGroupId(), hotelTransferData.getTransferHotelId(), key);
			return list;
		}else{
			 throw new BizException("pos_hotel_transfer转账表配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
	}

	@Override
	public void updateKaiyuanVipDiscount(long hotelGroupId, long hotelId,Date bizDate,String accnt,String cardno,String mode,BigDecimal dsc,BigDecimal srv,BigDecimal tax) {
		posMasterDao.updateKaiyuanVipDiscount(hotelGroupId, hotelId, bizDate, accnt,cardno,mode,dsc,srv,tax);
	}

	@Override
	public List<POSInterfaceCardFkDto> getKaiYuanCardFkList(long hotelGroupId,
			long hotelId, String key, String pccode) {
		PosUserDto posUserDto = new PosUserDto();
		posUserDto.setBizDate(UserManager.getBizDate());
		posUserDto.setCashier(UserManager.getCashier().toString());
		posUserDto.setHotelGroupId(hotelGroupId);
		posUserDto.setHotelId(hotelId);
		posUserDto.setPcCode(pccode);
		posUserDto.setTaCode(UserManager.getWorkStationCode());		
		posUserDto.setUserCode(UserManager.getUserCode());
//		posUserDto.setType1(type1);
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			KaiYuanCardPosDto kaiYuanCard = new KaiYuanCardPosDto();
			
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			kaiYuanCard =service.listKaiYuanDiscountModeOfVIP(posUserDto, key);
			if(kaiYuanCard == null){
				throw new BizException("没有查询到相关的卡信息！",BizExceptionConstant.INVALID_PARAM);
			}
			List<POSInterfaceCardFkDto> list = kaiYuanCardInfoChange(kaiYuanCard);
			return list;
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}
	}

	private String updateKaiYuanCheckOut(long hotelGroupId,long hotelId,String bizDate,String billno,BigDecimal pay,String type,String cardno,String flag,long gsts,String hotelCode,String accnt,String pccode){
		return kaiYuanInterfaceService.updateCardConsume(hotelGroupId, hotelId, UserManager.getBizDate(), billno, pay, type,cardno,flag,gsts,hotelCode,accnt,pccode);	
	}

	@Override
	public List<POSInterfaceCardFkDto> updateKaiYuanCardFkListByPos(
			long hotelGroupId, long hotelId, String accnt, String pccode,String type1,
			String stationCode, String userCode,Date bizDate, String key) {
		KaiYuanCardPosDto kaiYuanCard = kaiYuanInterfaceService.updateByCardSearchDtoForPos(hotelGroupId, hotelId, accnt, stationCode, key, userCode, pccode,type1);
		if(kaiYuanCard == null){
			throw new BizException("没有查询到相关的卡信息！",BizExceptionConstant.INVALID_PARAM);
		}
		List<POSInterfaceCardFkDto> list = kaiYuanCardInfoChange(kaiYuanCard);
		return list;
	}

	@Override
	public List<POSInterfaceCardFkDto> getCardLvKaList(long hotelGroupId,long hotelId, String key, String passWord,String isNeedPd) {
		PosUserDto posUserDto = new PosUserDto();
		posUserDto.setBizDate(UserManager.getBizDate());
		posUserDto.setCashier(UserManager.getCashier().toString());
		posUserDto.setHotelGroupId(hotelGroupId);
		posUserDto.setHotelId(hotelId);
		posUserDto.setPcCode("");
		posUserDto.setTaCode("");
		posUserDto.setUserCode(UserManager.getUserCode());	
		
		String pmsIp = getRemoteIp(hotelGroupId,hotelId);
		if(pmsIp != null && !pmsIp.equals("")){
			IPmsPosFacadeService service  = this.findInterfaceWithIp(pmsIp, IPmsPosFacadeService.class);
			try{
				List<POSInterfaceCardFkDto> list =service.listDiscountModeOfVIPLvKa(posUserDto, key, passWord,isNeedPd);
				return list;
			}catch(Exception e){				
				throw new BizException(e.getMessage(),BizExceptionConstant.INVALID_PARAM);
			}						
		}else{
			 throw new BizException("远程服务地址配置有误，请检查！",BizExceptionConstant.INVALID_PARAM);
		}			
	}	
}