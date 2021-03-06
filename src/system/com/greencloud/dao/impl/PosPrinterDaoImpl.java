package com.greencloud.dao.impl;
import com.aua.util.SQLHelper;
import com.aua.dao.impl.BaseDaoImpl;

import static com.aua.util.StringHelper.*;

import java.util.List;

import com.greencloud.entity.PosPrinter;
import com.greencloud.dao.IPosPrinterDao;

import org.springframework.dao.DataAccessException;

   /**
   * operate PosPrinter into db
   *@author 
   *@version 1.0
   *@date 2015-11-17 17:24
   */
  public class PosPrinterDaoImpl extends BaseDaoImpl implements IPosPrinterDao{

  /**
   *save posPrinter object  method
   *@param posPrinter PosPrinter 
   *@throws DataAccessException 
   *@author  
   *@version 1.0
   *@date 2015-11-17 17:24
   */
  public void save(PosPrinter posPrinter) throws DataAccessException {
       super.save(posPrinter);
  }
  
  /**
   *update posPrinter method
   *@param posPrinter PosPrinter
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-11-17 17:24
   */
  public void update(PosPrinter posPrinter) throws DataAccessException{
     super.update(posPrinter);
  }
  
   /**
   *save or update posPrinter object method
   *@param posPrinter PosPrinter
   *@throws DataAccessException
   *@author  
   *@version 1.0
   *@date 2015-11-17 17:24
   */
  public void saveOrUpdate(PosPrinter posPrinter) throws DataAccessException{
     super.saveOrUpdate(posPrinter);
  }
  
   /**
   *query posPrinter collection method
   *@param posPrinter PosPrinter send query vo 
   *@param firstResult 
   *@param maxResults 
   *@throws DataAccessException 
   *@author 
   *@version 1.0
   *@date 2015-11-17  17:24
   */
  public List<PosPrinter> list(PosPrinter posPrinter,int firstResult ,int maxResults)throws DataAccessException{
  	 SQLHelper sh = new SQLHelper("SELECT obj FROM PosPrinter obj WHERE 1=1 ");
  	 spellSQL(sh,posPrinter);
  	 sh.appendSql(" ORDER BY obj.id DESC");
  	 sh.setFirstResult(firstResult);
  	 sh.setMaxResults(maxResults);
  	 return find(sh);
  }
  
   /**
   *query collection method  
   *@param posPrinter PosPrinter send query vo 
   *@throws DataAccessException
   *@author 
   *@version 1.0
   *@date 2015-11-17 17:24
   */
  public List<PosPrinter> list(PosPrinter posPrinter)throws DataAccessException{
  	 SQLHelper sh = new SQLHelper("SELECT obj FROM PosPrinter obj WHERE 1=1 ");
  	 spellSQL(sh,posPrinter);
  	 sh.appendSql(" ORDER BY obj.id DESC");
  	 return find(sh);
  }
  
  /**
   *count obj amount
   *@param posPrinter PosPrinter query vo
   *@throws DataAccessException
   *@author 
   *@date 2015-11-17 17:24
   */
  public int count(PosPrinter posPrinter)throws DataAccessException
  {
    SQLHelper sh = new SQLHelper("SELECT count(obj) FROM PosPrinter obj WHERE 1=1 ");
    spellSQL(sh,posPrinter);
    return count(sh);
  }
  
   /**
   *query a posPrinter by id 
   *@param id 
   *@throws DataAccessException 
   *@author 
   *@date 2015-11-17 17:24
   */
  public PosPrinter load(Long id)throws DataAccessException
  {
    return load(PosPrinter.class ,id);
  }
  
  /*
   *basic spell SQL method 
   *@param posPrinter query vo
   *@throws DataAccessException 
   *@author weihuawon
   *@date 2015-11-17 17:24
   */
  private void spellSQL(SQLHelper sh,PosPrinter posPrinter)
  {
    if(posPrinter != null){
    	if(posPrinter.getId()!= null){
            sh.appendSql(" AND obj.id = ? ");
            sh.insertValue(posPrinter.getId());
         }
         if(posPrinter.getHotelGroupId()!= null){
            sh.appendSql(" AND obj.hotelGroupId = ? ");
            sh.insertValue(posPrinter.getHotelGroupId());
         }
         if(posPrinter.getHotelId()!= null){
            sh.appendSql(" AND obj.hotelId = ? ");
            sh.insertValue(posPrinter.getHotelId());
         }
        if(isNotNull(posPrinter.getCode())){
           sh.appendSql(" AND obj.code = ? ");
           sh.insertValue(posPrinter.getCode().trim());
        }
        if(isNotNull(posPrinter.getDescript())){
           sh.appendSql(" AND obj.descript = ? ");
           sh.insertValue(posPrinter.getDescript().trim());
        }
        if(isNotNull(posPrinter.getDescriptEn())){
           sh.appendSql(" AND obj.descriptEn = ? ");
           sh.insertValue(posPrinter.getDescriptEn().trim());
        }
        if(isNotNull(posPrinter.getIpAddr())){
           sh.appendSql(" AND obj.ipAddr = ? ");
           sh.insertValue(posPrinter.getIpAddr().trim());
        }
        if(isNotNull(posPrinter.getPrintMode())){
           sh.appendSql(" AND obj.printMode = ? ");
           sh.insertValue(posPrinter.getPrintMode().trim());
        }
        if(isNotNull(posPrinter.getPrinterType())){
           sh.appendSql(" AND obj.printerType = ? ");
           sh.insertValue(posPrinter.getPrinterType().trim());
        }
        if(isNotNull(posPrinter.getPrintServer())){
           sh.appendSql(" AND obj.printServer = ? ");
           sh.insertValue(posPrinter.getPrintServer().trim());
        }
        if(isNotNull(posPrinter.getDishChk())){
           sh.appendSql(" AND obj.dishChk = ? ");
           sh.insertValue(posPrinter.getDishChk().trim());
        }
        if(isNotNull(posPrinter.getSta())){
           sh.appendSql(" AND obj.sta = ? ");
           sh.insertValue(posPrinter.getSta().trim());
        }
        if(isNotNull(posPrinter.getOsta())){
           sh.appendSql(" AND obj.osta = ? ");
           sh.insertValue(posPrinter.getOsta().trim());
        }
        if(isNotNull(posPrinter.getValueSta())){
           sh.appendSql(" AND obj.valueSta = ? ");
           sh.insertValue(posPrinter.getValueSta().trim());
        }
        if(isNotNull(posPrinter.getPrinter1())){
           sh.appendSql(" AND obj.printer1 = ? ");
           sh.insertValue(posPrinter.getPrinter1().trim());
        }
        
        if(isNotNull(posPrinter.getCreateUser())){
            sh.appendSql(" AND obj.createUser = ? ");
            sh.insertValue(posPrinter.getCreateUser().trim());
         }
        if(posPrinter.getCreateDatetime()!= null){
            sh.appendSql(" AND obj.createDatetime = ? ");
            sh.insertValue(posPrinter.getCreateDatetime());
         }
         if(isNotNull(posPrinter.getModifyUser())){
            sh.appendSql(" AND obj.modifyUser = ? ");
            sh.insertValue(posPrinter.getModifyUser().trim());
         }
        if(posPrinter.getModifyDatetime()!= null){
            sh.appendSql(" AND obj.modifyDatetime = ? ");
            sh.insertValue(posPrinter.getModifyDatetime());
         }
    }
  }
}