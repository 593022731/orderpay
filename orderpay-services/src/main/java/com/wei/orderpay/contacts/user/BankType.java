package com.wei.orderpay.contacts.user;

public enum BankType {
	
	/**
	 * 占位符，没有意义，因为业务值从1开始
	 */
	empty(0,"",""),
	
	ICBC(1,"中国工商银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/47/4bd7407cb7fdc04f"),
	
	CCB(2,"中国建设银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/a1/c23e49fca7a98c63"),
	
	BOC(3,"中国银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/13/e7674cd0ad9ce567"),
	
	BOCOM(4,"交通银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/9e/4e6c4ee5830482c3"),
	
	ABC(5,"中国农业银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/45/874c448aa5fe0925"),
	
	CMB(6,"招商银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/cb/2f774e3eb7d5d7e2"),
	
	PSBC(7,"中国邮政储蓄银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/6e/d896438c92235091"),
	
	CEB(8,"中国光大银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/72/c88b4b509b1e09eb"),

	MBC(9,"中国民生银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/1a/b0b64f8bb8bfd659"),
	
	PAB(10,"平安银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/80/ee154d12aae7207f"),
	
	SPDB(11,"浦发银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/54/877e4fa6aaa8d17a"),
	
	ECITIC(12,"中信银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/81/70fb49e1927fc819"),
	
	CIB(13,"兴业银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/9b/e11241d1958e526a"),
	
	HXB(14,"华夏银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/f0/9fe049c38a115c60"),
	
	GDB(15,"广发银行","http://7xi9yp.com1.z0.glb.clouddn.com/bankcard_avatar/99/dda64cfebf5e7a8f");

	public int bankType;
	public String title;
	public String logo;
	
	private BankType(int bankType,String title,String logo){
		this.bankType = bankType;
		this.title = title;
		this.logo = logo;
	}
	
	public static BankType getBankType(int bankType){
		for(BankType item : BankType.values()){
			if(item.bankType == bankType){
				return item;
			}
		}
		return null;
	}
	
}
