package com.greencloud.dao.impl;
import com.aua.util.SQLHelper;
import com.aua.dao.impl.BaseDaoImpl;

import static com.aua.util.StringHelper.*;

import java.util.List;

import com.greencloud.entity.PosPccodeAuditFlag;
import com.greencloud.dao.IPosPccodeAuditFlagDao;

import org.springframework.dao.DataAccessException;

   /**
   * operate PosPccodeAuditFlag into db
   *@author 
   *@version 1.0
   *@date 2016-03-30 10:39
   */
  public class PosPccodeAuditFlagDaoImpl extends BaseDaoImpl implements IPosPccodeAuditFlagDao{

  /**
   *save posPccodeAuditFlag object  method
   *@param posPccodeAuditFlag PosPccodeAuditFlag 
   *@throws DataAccessException 
   *@author  
   *@version 1.0
   *@date 2016-03-30 10:39
   */
  public void save(PosPccodeAuditFlag posPccodeAuditFlag) throws DataAccessException {
       super.save(posPccodeAuditFlag);
  }
  
  /**
   *update posPccodeAuditFlag method
   *@param posPccodeAuditFlag PosPccodeAuditFlag
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2016-03-30 10:39
   */
  public void update(PosPccodeAuditFlag posPccodeAuditFlag) throws DataAccessException{
     super.update(posPccodeAuditFlag);
  }
  
   /**
   *save or update posPccodeAuditFlag object method
   *@param posPccodeAuditFlag PosPccodeAuditFlag
   *@throws DataAccessException
   *@author  
   *@version 1.0
   *@date 2016-03-30 10:39
   */
  public void saveOrUpdate(PosPccodeAuditFlag posPccodeAuditFlag) throws DataAccessException{
     super.saveOrUpdate(posPccodeAuditFlag);
  }
  
   /**
   *query posPccodeAuditFlag collection method
   *@param posPccodeAuditFlag PosPccodeAuditFlag send query vo 
   *@param firstResult 
   *@param maxResults 
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2016-03-30  10:39
   */
  public List<PosPccodeAuditFlag> list(PosPccodeAuditFlag posPccodeAuditFlag,int firstResult ,int maxResults)throws DataAccessException{
  	 SQLHelper sh = new SQLHelper("SELECT obj FROM PosPccodeAuditFlag obj WHERE 1=1 ");
  	 spellSQL(sh,posPccodeAuditFlag);
  	 sh.appendSql(" ORDER BY obj.id DESC");
  	 sh.setFirstResult(firstResult);
  	 sh.setMaxResults(maxResults);
  	 return find(sh);
  }
  
   /**
   *query collection method  
   *@param posPccodeAuditFlag PosPccodeAuditFlag send query vo 
   *@throws DataAccessException
   *@author 
   *@version 1.0
   *@date 2016-03-30 10:39
   */
  public List<PosPccodeAuditFlag> list(PosPccodeAuditFlag posPccodeAuditFlag)throws DataAccessException{
  	 SQLHelper sh = new SQLHelper("SELECT obj FROM PosPccodeAuditFlag obj WHERE 1=1 ");
  	 spellSQL(sh,posPccodeAuditFlag);
  	 sh.appendSql(" ORDER BY obj.id DESC");
  	 return find(sh);
  }
  
  /**
   *count obj amount
   *@param posPccodeAuditFlag PosPccodeAuditFlag query vo
   *@throws DataAccessException
   *@author 
   *@date 2016-03-30 10:39
   */
  public int count(PosPccodeAuditFlag posPccodeAuditFlag)throws DataAccessException
  {
    SQLHelper sh = new SQLHelper("SELECT count(obj) FROM PosPccodeAuditFlag obj WHERE 1=1 ");
    spellSQL(sh,posPccodeAuditFlag);
    return count(sh);
  }
  
   /**
   *query a posPccodeAuditFlag by id 
   *@param id 
   *@throws DataAccessException 
   *@author 
   *@date 2016-03-30 10:39
   */
  public PosPccodeAuditFlag load(Long id)throws DataAccessException
  {
    return load(PosPccodeAuditFlag.class ,id);
  }
  
  /*
   *basic spell SQL method 
   *@param posPccodeAuditFlag query vo
   *@throws DataAccessException 
   *@author weihuawon
   *@date 2016-03-30 10:39
   */
  private void spellSQL(SQLHelper sh,PosPccodeAuditFlag posPccodeAuditFlag)
  {
    if(posPccodeAuditFlag != null){
    	if(posPccodeAuditFlag.getId()!= null){
            sh.appendSql(" AND obj.id = ? ");
            sh.insertValue(posPccodeAuditFlag.getId());
         }
         if(posPccodeAuditFlag.getHotelGroupId()!= null){
            sh.appendSql(" AND obj.hotelGroupId = ? ");
            sh.insertValue(posPccodeAuditFlag.getHotelGroupId());
         }
         if(posPccodeAuditFlag.getHotelId()!= null){
            sh.appendSql(" AND obj.hotelId = ? ");
            sh.insertValue(posPccodeAuditFlag.getHotelId());
         }
        if(isNotNull(posPccodeAuditFlag.getPosPccode())){
           sh.appendSql(" AND obj.posPccode = ? ");
           sh.insertValue(posPccodeAuditFlag.getPosPccode().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getPosPccodeDescript())){
           sh.appendSql(" AND obj.posPccodeDescript = ? ");
           sh.insertValue(posPccodeAuditFlag.getPosPccodeDescript().trim());
        }
       if(posPccodeAuditFlag.getBizDate()!= null){
           sh.appendSql(" AND obj.bizDate = ? ");
           sh.insertValue(posPccodeAuditFlag.getBizDate());
        }
       if(posPccodeAuditFlag.getBizDate1()!= null){
           sh.appendSql(" AND obj.bizDate1 = ? ");
           sh.insertValue(posPccodeAuditFlag.getBizDate1());
        }
        if(isNotNull(posPccodeAuditFlag.getIsAudit())){
           sh.appendSql(" AND obj.isAudit = ? ");
           sh.insertValue(posPccodeAuditFlag.getIsAudit().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getAuditUser())){
           sh.appendSql(" AND obj.auditUser = ? ");
           sh.insertValue(posPccodeAuditFlag.getAuditUser().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getStationAudit())){
           sh.appendSql(" AND obj.stationAudit = ? ");
           sh.insertValue(posPccodeAuditFlag.getStationAudit().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getIsRmposted())){
           sh.appendSql(" AND obj.isRmposted = ? ");
           sh.insertValue(posPccodeAuditFlag.getIsRmposted().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getStationRmcheck())){
           sh.appendSql(" AND obj.stationRmcheck = ? ");
           sh.insertValue(posPccodeAuditFlag.getStationRmcheck().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getStationRmpost())){
           sh.appendSql(" AND obj.stationRmpost = ? ");
           sh.insertValue(posPccodeAuditFlag.getStationRmpost().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getIsExcludePart())){
           sh.appendSql(" AND obj.isExcludePart = ? ");
           sh.insertValue(posPccodeAuditFlag.getIsExcludePart().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getStationExcludePart())){
           sh.appendSql(" AND obj.stationExcludePart = ? ");
           sh.insertValue(posPccodeAuditFlag.getStationExcludePart().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getIsCanCheckout())){
           sh.appendSql(" AND obj.isCanCheckout = ? ");
           sh.insertValue(posPccodeAuditFlag.getIsCanCheckout().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getIsKeypart())){
           sh.appendSql(" AND obj.isKeypart = ? ");
           sh.insertValue(posPccodeAuditFlag.getIsKeypart().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getIsYearFirstDay())){
           sh.appendSql(" AND obj.isYearFirstDay = ? ");
           sh.insertValue(posPccodeAuditFlag.getIsYearFirstDay().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getIsMonthFirstDay())){
           sh.appendSql(" AND obj.isMonthFirstDay = ? ");
           sh.insertValue(posPccodeAuditFlag.getIsMonthFirstDay().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getIsReportDone())){
           sh.appendSql(" AND obj.isReportDone = ? ");
           sh.insertValue(posPccodeAuditFlag.getIsReportDone().trim());
        }
        if(isNotNull(posPccodeAuditFlag.getCreateUser())){
            sh.appendSql(" AND obj.createUser = ? ");
            sh.insertValue(posPccodeAuditFlag.getCreateUser().trim());
         }
        if(posPccodeAuditFlag.getCreateDatetime()!= null){
            sh.appendSql(" AND obj.createDatetime = ? ");
            sh.insertValue(posPccodeAuditFlag.getCreateDatetime());
         }
         if(isNotNull(posPccodeAuditFlag.getModifyUser())){
            sh.appendSql(" AND obj.modifyUser = ? ");
            sh.insertValue(posPccodeAuditFlag.getModifyUser().trim());
         }
        if(posPccodeAuditFlag.getModifyDatetime()!= null){
            sh.appendSql(" AND obj.modifyDatetime = ? ");
            sh.insertValue(posPccodeAuditFlag.getModifyDatetime());
         }
    }
  }
}