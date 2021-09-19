package com.sample;


public class MqOrder {

  private long id;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;
  private long status;
  private long userId;
  private String describe;
  private String productIdNum;
  private long orderMessageStatus;
  private long orderStatus;
  private String onlyId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }


  public String getProductIdNum() {
    return productIdNum;
  }

  public void setProductIdNum(String productIdNum) {
    this.productIdNum = productIdNum;
  }


  public long getOrderMessageStatus() {
    return orderMessageStatus;
  }

  public void setOrderMessageStatus(long orderMessageStatus) {
    this.orderMessageStatus = orderMessageStatus;
  }


  public long getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(long orderStatus) {
    this.orderStatus = orderStatus;
  }


  public String getOnlyId() {
    return onlyId;
  }

  public void setOnlyId(String onlyId) {
    this.onlyId = onlyId;
  }

}
