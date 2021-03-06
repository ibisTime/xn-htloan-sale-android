package com.cdkj.wzcd.model;

import java.util.List;

/**
 * Created by cdkj on 2018/6/4.
 */

public class NodeListModel  {



    //====================   老的bean=====================================


    /**
     * code : BO201806021733126654660
     * repayBizCode : RB201806021742154411542
     * loanProductCode : LP201806010719319062250
     * loanProductName : 贷款产品001
     * loanBank : BA201806010718324895638
     * gpsFee : 12000
     * authFee : 10000
     * bankFee : 11000
     * monthRate : 0.1
     * creditCode : C201806021726455483130
     * bizType : 0
     * loanPeriod : 12
     * invoiceCompany : 齐天大圣开票单位
     * carBrand : 齐天大圣品牌
     * originalPrice : 400000000
     * invoicePrice : 500000000
     * carColor : 齐天大圣颜色
     * monthDeposit : 22916670
     * firstAmount : 250000000
     * firstRate : 50
     * loanAmount : 250000000
     * settleAddress : 齐天大圣落户地点
     * applyUserId : U201806010711055712827
     * applyUserName : 齐天大圣
     * gender : 1
     * marryState : 1
     * nation : 齐天大圣民族
     * education : 1
     * idNo : 150404199409090612
     * familyNumber : 5
     * mobile : 13282838237
     * nowAddress : 齐天大圣现居住地址
     * postCode1 : 630000
     * residenceAddress : 齐天大圣户口所在地
     * postCode2 : 640000
     * familyMainAsset : 齐天大圣家庭主要财产
     * mainAssetInclude : 齐天大圣主要财产包括
     * mainIncome : 1
     * workCompanyName : 齐天大圣工作单位名称
     * workCompanyAddress : 齐天大圣工作单位地址
     * selfCompanyArea : 50000
     * employeeQuantity : 500
     * enterpriseMonthOutput : 1000000
     * position : 齐天大圣职位
     * postTitle : 齐天大圣职称
     * monthIncome : 1000000
     * mateName : 配偶信息姓名
     * mateMobile : 13282838237
     * mateIdNo : 150404199409090612
     * mateEducation : 1
     * mateCompanyName : 配偶信息工作单位名称
     * mateCompanyAddress : 配偶信息工作单位地址
     * mateCompanyContactNo : 18976888
     * guaName : 七牛
     * guaMobile : 13282838237
     * guaIdNo : 150404199409090612
     * guaPhone : 1234567
     * guaCompanyName : 七牛工作单位名称
     * guaCompanyAddress : 七牛工作单位地址
     * guaHouseAssetAddress : 七牛担保人房产地址
     * emergencyName1 : 联系人1姓名
     * emergencyRelation1 : 联系人1与申请人关系
     * emergencyMobile1 : 15545555555
     * emergencyName2 : 联系人2姓名
     * emergencyRelation2 : 联系人2姓名与申请人关系
     * emergencyMobile2 : 15545555555
     * jourDatetimeStart : Jun 2, 2018 12:00:00 AM
     * jourDatetimeEnd : Jul 20, 2018 12:00:00 AM
     * jourIncome : 50000
     * jourExpend : 9000
     * jourBalance : 999999
     * jourMonthIncome : 88888
     * jourMonthExpend : 77777
     * jourRemark : 备注
     * houseContract : FvOqg19Too_pSXZUkydOQl_OnFWv
     * housePicture : FvOqg19Too_pSXZUkydOQl_OnFWv
     * isAdvanceFund : 1
     * interviewVideo : FvTw5nazUbBwSRoJ8DpDUWAPcKCg
     * interviewContract : Fs6kQcEiNS4USu5Mp6euL7_mKwtL
     * advanceFundDatetime : Jun 2, 2018 12:00:00 AM
     * advanceFundAmount : 250000000
     * carSettleDatetime : Jun 2, 2018 12:00:00 AM
     * carNumber : 123456
     * carInvoice : FvOqg19Too_pSXZUkydOQl_OnFWv
     * carHgz : FvOqg19Too_pSXZUkydOQl_OnFWv
     * carJqx : FvOqg19Too_pSXZUkydOQl_OnFWv
     * carSyx : FvOqg19Too_pSXZUkydOQl_OnFWv
     * carRegcerti : FvOqg19Too_pSXZUkydOQl_OnFWv
     * carPd : FvOqg19Too_pSXZUkydOQl_OnFWv
     * carKey : FvOqg19Too_pSXZUkydOQl_OnFWv
     * carBigSmj : FvOqg19Too_pSXZUkydOQl_OnFWv
     * bankCommitDatetime : Jun 2, 2018 12:00:00 AM
     * repayBankcardNumber : 1111111111111111111
     * repayBillDate : 11
     * repayBankDate : 11
     * repayFirstMonthAmount : 11111000
     * repayFirstMonthDatetime : Jun 2, 2018 12:00:00 AM
     * repayMonthAmount : 111000
     * receiptBankCode : CMBC
     * receiptBankcardNumber : 333333333333
     * receiptPdf : FvOqg19Too_pSXZUkydOQl_OnFWv
     * receiptRemark : 备注
     * pledgeDatetime : Jun 2, 2018 12:00:00 AM
     * greenBigSmj : FvOqg19Too_pSXZUkydOQl_OnFWv
     * pledgeBankCommitDatetime : Jun 2, 2018 12:00:00 AM
     * saleUserId : USYS201800000000001
     * companyCode : DP201800000000000000001
     * applyDatetime : Jun 2, 2018 5:33:12 PM
     * curNodeCode : 002_06
     * remark : 提交说明
     * budgetOrderGpsList : [{"code":"GPSAZ201806051451029871331","gpsDevNo":"G0005","gpsType":"1","azLocation":"位置","azDatetime":"2018-06-05 00:00:00.0","azUser":"人员","remark":"备注","budgetOrder":"BO201806050154003632973"}]
     * credit : {"code":"C201806021726455483130","loanBankCode":"BA201806010714278021343","loanAmount":250000000,"bizType":"1","companyCode":"DP201800000000000000001","saleUserId":"USYS201800000000001","applyDatetime":"Jun 2, 2018 5:26:45 PM","curNodeCode":"001_09","creditUserList":[{"code":"CU201806021726455582359","creditCode":"C201806021726455483130","userName":"齐天大圣","relation":"1","loanRole":"1","mobile":"13282838237","idNo":"150404199409090612","idNoFront":"FvOqg19Too_pSXZUkydOQl_OnFWv","idNoReverse":"FvOqg19Too_pSXZUkydOQl_OnFWv","authPdf":"FvOqg19Too_pSXZUkydOQl_OnFWv","interviewPic":"FvOqg19Too_pSXZUkydOQl_OnFWv","bankCreditResultPdf":"FvOqg19Too_pSXZUkydOQl_OnFWv"}]}
     * companyName : 乌鲁木齐华途威通汽车销售有限公司
     * loanBankName : 招商银行
     */

    private String code;
    private String repayBizCode;
    private String loanProductCode;
    private String loanProductName;
    private String loanBank;
    private int gpsFee;
    private int authFee;
    private int bankFee;
    private double monthRate;
    private String creditCode;
    private String bizType;
    private String loanPeriod;
    private String invoiceCompany;
    private String carBrand;
    private int originalPrice;
    private int invoicePrice;
    private String carColor;
    private int monthDeposit;
    private int firstAmount;
    private int firstRate;
//    private String loanAmount;
    private String settleAddress;
    private String applyUserId;
    private String applyUserName;
    private String gender;
    private String marryState;
    private String nation;
    private String education;
    private String idNo;
    private String familyNumber;
    private String mobile;
    private String nowAddress;
    private String postCode1;
    private String residenceAddress;
    private String postCode2;
    private String familyMainAsset;
    private String mainAssetInclude;
    private String mainIncome;
    private String workCompanyName;
    private String workCompanyAddress;
    private String selfCompanyArea;
    private String employeeQuantity;
    private String enterpriseMonthOutput;
    private String position;
    private String postTitle;
    private String monthIncome;
    private String mateName;
    private String mateMobile;
    private String mateIdNo;
    private String mateEducation;
    private String mateCompanyName;
    private String mateCompanyAddress;
    private String mateCompanyContactNo;
    private String guaName;
    private String guaMobile;
    private String guaIdNo;
    private String guaPhone;
    private String guaCompanyName;
    private String guaCompanyAddress;
    private String guaHouseAssetAddress;
    private String emergencyName1;
    private String emergencyRelation1;
    private String emergencyMobile1;
    private String emergencyName2;
    private String emergencyRelation2;
    private String emergencyMobile2;
    private String jourDatetimeStart;
    private String jourDatetimeEnd;
    private int jourIncome;
    private int jourExpend;
    private int jourBalance;
    private int jourMonthIncome;
    private int jourMonthExpend;
    private String jourRemark;
    private String houseContract;
    private String housePicture;
    private String isAdvanceFund;
    private String interviewVideo;
    private String interviewContract;
    private String advanceFundDatetime;
    private String advanceFundAmount;
    private String carSettleDatetime;
//    private String carNumber;
    private String carInvoice;
    private String carHgz;
    private String carJqx;
    private String carSyx;
    private String carSettleOtherPdf;
//    private String carRegcerti;
//    private String carPd;
//    private String carKey;
//    private String carBigSmj;
    private String bankCommitDatetime;
    private String repayBankcardNumber;
    private String repayBillDate;
    private String repayBankDate;
    private int repayFirstMonthAmount;
    private String repayFirstMonthDatetime;
    private int repayMonthAmount;
    private String receiptBankCode;
    private String receiptBankcardNumber;
    private String receiptPdf;
    private String receiptRemark;
    private String pledgeDatetime;
    private String greenBigSmj;
    private String pledgeBankCommitDatetime;
    private String pledgeUserIdCardCopy;
    private String saleUserId;
    private String companyCode;
    private String applyDatetime;
    private String curNodeCode;
    private String remark;
    private CreditBean credit;
    private String companyName;
    private String loanBankName;
    private String policyDatetime;
    private String saleUserName;//信贷
    private String insideJobName;//内勤
    private String policyDueDate;
    private String teamName;

    private String areaName;
    private String loanAmount;
    private String carModel;
    private String carFrameNo;
    private String carBigSmj;
    private String carKey;
    private String carNumber;
    private String carPd;
    private String carRegcerti;
    private String carXszSmj;
    private String dutyPaidProveSmj;
    private String advanfCurNodeCode;
    private String intevCurNodeCode;
    private int age;
    private String pledgeUser;
    private String pledgeAddress;
    private String supplementNote;
    private String repaySubbranch;

    public String getRepaySubbranch() {
        return repaySubbranch;
    }

    public void setRepaySubbranch(String repaySubbranch) {
        this.repaySubbranch = repaySubbranch;
    }

    public String getSupplementNote() {
        return supplementNote;
    }

    public void setSupplementNote(String supplementNote) {
        this.supplementNote = supplementNote;
    }

    public String getPledgeAddress() {
        return pledgeAddress;
    }

    public void setPledgeAddress(String pledgeAddress) {
        this.pledgeAddress = pledgeAddress;
    }

    public String getPledgeUser() {
        return pledgeUser;
    }

    public void setPledgeUser(String pledgeUser) {
        this.pledgeUser = pledgeUser;
    }

    public String getIntevCurNodeCode() {
        return intevCurNodeCode;
    }

    public void setIntevCurNodeCode(String intevCurNodeCode) {
        this.intevCurNodeCode = intevCurNodeCode;
    }

    //    carBigSmj	必填，大本扫描件	string
//    carKey	必填，车钥匙	string
//    carNumber	必填，车牌号	string
//    carPd	必填，车辆批单	string
//    carRegcerti	必填，登记证书	string
//    carXszSmj	必填，车辆行驶证扫描件	string
//    code	必填，预算单编号	string
//    dutyPaidProveSmj	必填，完税证明扫描件	string

    public String getAdvanfCurNodeCode() {
        return advanfCurNodeCode;
    }

    public void setAdvanfCurNodeCode(String advanfCurNodeCode) {
        this.advanfCurNodeCode = advanfCurNodeCode;
    }

    private List<BudgetOrderGpsListBean> budgetOrderGpsList;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarFrameNo() {
        return carFrameNo;
    }

    public void setCarFrameNo(String carFrameNo) {
        this.carFrameNo = carFrameNo;
    }

    public String getCarXszSmj() {
        return carXszSmj;
    }

    public void setCarXszSmj(String carXszSmj) {
        this.carXszSmj = carXszSmj;
    }

    public String getDutyPaidProveSmj() {
        return dutyPaidProveSmj;
    }

    public void setDutyPaidProveSmj(String dutyPaidProveSmj) {
        this.dutyPaidProveSmj = dutyPaidProveSmj;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPledgeUserIdCardCopy() {
        return pledgeUserIdCardCopy;
    }

    public void setPledgeUserIdCardCopy(String pledgeUserIdCardCopy) {
        this.pledgeUserIdCardCopy = pledgeUserIdCardCopy;
    }

    public String getSaleUserName() {
        return saleUserName;
    }

    public void setSaleUserName(String saleUserName) {
        this.saleUserName = saleUserName;
    }

    public String getInsideJobName() {
        return insideJobName;
    }

    public void setInsideJobName(String insideJobName) {
        this.insideJobName = insideJobName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPolicyDueDate() {
        return policyDueDate;
    }

    public void setPolicyDueDate(String policyDueDate) {
        this.policyDueDate = policyDueDate;
    }


    public String getPolicyDatetime() {
        return policyDatetime;
    }

    public void setPolicyDatetime(String policyDatetime) {
        this.policyDatetime = policyDatetime;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRepayBizCode() {
        return repayBizCode;
    }

    public void setRepayBizCode(String repayBizCode) {
        this.repayBizCode = repayBizCode;
    }

    public String getLoanProductCode() {
        return loanProductCode;
    }

    public void setLoanProductCode(String loanProductCode) {
        this.loanProductCode = loanProductCode;
    }

    public String getLoanProductName() {
        return loanProductName;
    }

    public void setLoanProductName(String loanProductName) {
        this.loanProductName = loanProductName;
    }

    public String getLoanBank() {
        return loanBank;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank;
    }

    public int getGpsFee() {
        return gpsFee;
    }

    public void setGpsFee(int gpsFee) {
        this.gpsFee = gpsFee;
    }

    public int getAuthFee() {
        return authFee;
    }

    public void setAuthFee(int authFee) {
        this.authFee = authFee;
    }

    public int getBankFee() {
        return bankFee;
    }

    public void setBankFee(int bankFee) {
        this.bankFee = bankFee;
    }

    public double getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(double monthRate) {
        this.monthRate = monthRate;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(int invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public int getMonthDeposit() {
        return monthDeposit;
    }

    public void setMonthDeposit(int monthDeposit) {
        this.monthDeposit = monthDeposit;
    }

    public int getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(int firstAmount) {
        this.firstAmount = firstAmount;
    }

    public int getFirstRate() {
        return firstRate;
    }

    public void setFirstRate(int firstRate) {
        this.firstRate = firstRate;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getSettleAddress() {
        return settleAddress;
    }

    public void setSettleAddress(String settleAddress) {
        this.settleAddress = settleAddress;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarryState() {
        return marryState;
    }

    public void setMarryState(String marryState) {
        this.marryState = marryState;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getFamilyNumber() {
        return familyNumber;
    }

    public void setFamilyNumber(String familyNumber) {
        this.familyNumber = familyNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNowAddress() {
        return nowAddress;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }

    public String getPostCode1() {
        return postCode1;
    }

    public void setPostCode1(String postCode1) {
        this.postCode1 = postCode1;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getPostCode2() {
        return postCode2;
    }

    public void setPostCode2(String postCode2) {
        this.postCode2 = postCode2;
    }

    public String getFamilyMainAsset() {
        return familyMainAsset;
    }

    public void setFamilyMainAsset(String familyMainAsset) {
        this.familyMainAsset = familyMainAsset;
    }

    public String getMainAssetInclude() {
        return mainAssetInclude;
    }

    public void setMainAssetInclude(String mainAssetInclude) {
        this.mainAssetInclude = mainAssetInclude;
    }

    public String getMainIncome() {
        return mainIncome;
    }

    public void setMainIncome(String mainIncome) {
        this.mainIncome = mainIncome;
    }

    public String getWorkCompanyName() {
        return workCompanyName;
    }

    public void setWorkCompanyName(String workCompanyName) {
        this.workCompanyName = workCompanyName;
    }

    public String getWorkCompanyAddress() {
        return workCompanyAddress;
    }

    public void setWorkCompanyAddress(String workCompanyAddress) {
        this.workCompanyAddress = workCompanyAddress;
    }

    public String getSelfCompanyArea() {
        return selfCompanyArea;
    }

    public void setSelfCompanyArea(String selfCompanyArea) {
        this.selfCompanyArea = selfCompanyArea;
    }

    public String getEmployeeQuantity() {
        return employeeQuantity;
    }

    public void setEmployeeQuantity(String employeeQuantity) {
        this.employeeQuantity = employeeQuantity;
    }

    public String getEnterpriseMonthOutput() {
        return enterpriseMonthOutput;
    }

    public void setEnterpriseMonthOutput(String enterpriseMonthOutput) {
        this.enterpriseMonthOutput = enterpriseMonthOutput;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    public String getMateName() {
        return mateName;
    }

    public void setMateName(String mateName) {
        this.mateName = mateName;
    }

    public String getMateMobile() {
        return mateMobile;
    }

    public void setMateMobile(String mateMobile) {
        this.mateMobile = mateMobile;
    }

    public String getMateIdNo() {
        return mateIdNo;
    }

    public void setMateIdNo(String mateIdNo) {
        this.mateIdNo = mateIdNo;
    }

    public String getMateEducation() {
        return mateEducation;
    }

    public void setMateEducation(String mateEducation) {
        this.mateEducation = mateEducation;
    }

    public String getMateCompanyName() {
        return mateCompanyName;
    }

    public void setMateCompanyName(String mateCompanyName) {
        this.mateCompanyName = mateCompanyName;
    }

    public String getMateCompanyAddress() {
        return mateCompanyAddress;
    }

    public void setMateCompanyAddress(String mateCompanyAddress) {
        this.mateCompanyAddress = mateCompanyAddress;
    }

    public String getMateCompanyContactNo() {
        return mateCompanyContactNo;
    }

    public void setMateCompanyContactNo(String mateCompanyContactNo) {
        this.mateCompanyContactNo = mateCompanyContactNo;
    }

    public String getGuaName() {
        return guaName;
    }

    public void setGuaName(String guaName) {
        this.guaName = guaName;
    }

    public String getGuaMobile() {
        return guaMobile;
    }

    public void setGuaMobile(String guaMobile) {
        this.guaMobile = guaMobile;
    }

    public String getGuaIdNo() {
        return guaIdNo;
    }

    public void setGuaIdNo(String guaIdNo) {
        this.guaIdNo = guaIdNo;
    }

    public String getGuaPhone() {
        return guaPhone;
    }

    public void setGuaPhone(String guaPhone) {
        this.guaPhone = guaPhone;
    }

    public String getGuaCompanyName() {
        return guaCompanyName;
    }

    public void setGuaCompanyName(String guaCompanyName) {
        this.guaCompanyName = guaCompanyName;
    }

    public String getGuaCompanyAddress() {
        return guaCompanyAddress;
    }

    public void setGuaCompanyAddress(String guaCompanyAddress) {
        this.guaCompanyAddress = guaCompanyAddress;
    }

    public String getGuaHouseAssetAddress() {
        return guaHouseAssetAddress;
    }

    public void setGuaHouseAssetAddress(String guaHouseAssetAddress) {
        this.guaHouseAssetAddress = guaHouseAssetAddress;
    }

    public String getEmergencyName1() {
        return emergencyName1;
    }

    public void setEmergencyName1(String emergencyName1) {
        this.emergencyName1 = emergencyName1;
    }

    public String getEmergencyRelation1() {
        return emergencyRelation1;
    }

    public void setEmergencyRelation1(String emergencyRelation1) {
        this.emergencyRelation1 = emergencyRelation1;
    }

    public String getEmergencyMobile1() {
        return emergencyMobile1;
    }

    public void setEmergencyMobile1(String emergencyMobile1) {
        this.emergencyMobile1 = emergencyMobile1;
    }

    public String getEmergencyName2() {
        return emergencyName2;
    }

    public void setEmergencyName2(String emergencyName2) {
        this.emergencyName2 = emergencyName2;
    }

    public String getEmergencyRelation2() {
        return emergencyRelation2;
    }

    public void setEmergencyRelation2(String emergencyRelation2) {
        this.emergencyRelation2 = emergencyRelation2;
    }

    public String getEmergencyMobile2() {
        return emergencyMobile2;
    }

    public void setEmergencyMobile2(String emergencyMobile2) {
        this.emergencyMobile2 = emergencyMobile2;
    }

    public String getJourDatetimeStart() {
        return jourDatetimeStart;
    }

    public void setJourDatetimeStart(String jourDatetimeStart) {
        this.jourDatetimeStart = jourDatetimeStart;
    }

    public String getJourDatetimeEnd() {
        return jourDatetimeEnd;
    }

    public void setJourDatetimeEnd(String jourDatetimeEnd) {
        this.jourDatetimeEnd = jourDatetimeEnd;
    }

    public int getJourIncome() {
        return jourIncome;
    }

    public void setJourIncome(int jourIncome) {
        this.jourIncome = jourIncome;
    }

    public int getJourExpend() {
        return jourExpend;
    }

    public void setJourExpend(int jourExpend) {
        this.jourExpend = jourExpend;
    }

    public int getJourBalance() {
        return jourBalance;
    }

    public void setJourBalance(int jourBalance) {
        this.jourBalance = jourBalance;
    }

    public int getJourMonthIncome() {
        return jourMonthIncome;
    }

    public void setJourMonthIncome(int jourMonthIncome) {
        this.jourMonthIncome = jourMonthIncome;
    }

    public int getJourMonthExpend() {
        return jourMonthExpend;
    }

    public void setJourMonthExpend(int jourMonthExpend) {
        this.jourMonthExpend = jourMonthExpend;
    }

    public String getJourRemark() {
        return jourRemark;
    }

    public void setJourRemark(String jourRemark) {
        this.jourRemark = jourRemark;
    }

    public String getHouseContract() {
        return houseContract;
    }

    public void setHouseContract(String houseContract) {
        this.houseContract = houseContract;
    }

    public String getHousePicture() {
        return housePicture;
    }

    public void setHousePicture(String housePicture) {
        this.housePicture = housePicture;
    }

    public String getIsAdvanceFund() {
        return isAdvanceFund;
    }

    public void setIsAdvanceFund(String isAdvanceFund) {
        this.isAdvanceFund = isAdvanceFund;
    }

    public String getInterviewVideo() {
        return interviewVideo;
    }

    public void setInterviewVideo(String interviewVideo) {
        this.interviewVideo = interviewVideo;
    }

    public String getInterviewContract() {
        return interviewContract;
    }

    public void setInterviewContract(String interviewContract) {
        this.interviewContract = interviewContract;
    }

    public String getAdvanceFundDatetime() {
        return advanceFundDatetime;
    }

    public void setAdvanceFundDatetime(String advanceFundDatetime) {
        this.advanceFundDatetime = advanceFundDatetime;
    }

    public String getAdvanceFundAmount() {
        return advanceFundAmount;
    }

    public void setAdvanceFundAmount(String advanceFundAmount) {
        this.advanceFundAmount = advanceFundAmount;
    }

    public String getCarSettleDatetime() {
        return carSettleDatetime;
    }

    public void setCarSettleDatetime(String carSettleDatetime) {
        this.carSettleDatetime = carSettleDatetime;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarInvoice() {
        return carInvoice;
    }

    public void setCarInvoice(String carInvoice) {
        this.carInvoice = carInvoice;
    }

    public String getCarHgz() {
        return carHgz;
    }

    public void setCarHgz(String carHgz) {
        this.carHgz = carHgz;
    }

    public String getCarJqx() {
        return carJqx;
    }

    public void setCarJqx(String carJqx) {
        this.carJqx = carJqx;
    }

    public String getCarSettleOtherPdf() {
        return carSettleOtherPdf;
    }

    public void setCarSettleOtherPdf(String carSettleOtherPdf) {
        this.carSettleOtherPdf = carSettleOtherPdf;
    }

    public String getCarSyx() {
        return carSyx;
    }

    public void setCarSyx(String carSyx) {
        this.carSyx = carSyx;
    }

    public String getCarRegcerti() {
        return carRegcerti;
    }

    public void setCarRegcerti(String carRegcerti) {
        this.carRegcerti = carRegcerti;
    }

    public String getCarPd() {
        return carPd;
    }

    public void setCarPd(String carPd) {
        this.carPd = carPd;
    }

    public String getCarKey() {
        return carKey;
    }

    public void setCarKey(String carKey) {
        this.carKey = carKey;
    }

    public String getCarBigSmj() {
        return carBigSmj;
    }

    public void setCarBigSmj(String carBigSmj) {
        this.carBigSmj = carBigSmj;
    }

    public String getBankCommitDatetime() {
        return bankCommitDatetime;
    }

    public void setBankCommitDatetime(String bankCommitDatetime) {
        this.bankCommitDatetime = bankCommitDatetime;
    }

    public String getRepayBankcardNumber() {
        return repayBankcardNumber;
    }

    public void setRepayBankcardNumber(String repayBankcardNumber) {
        this.repayBankcardNumber = repayBankcardNumber;
    }

    public String getRepayBillDate() {
        return repayBillDate;
    }

    public void setRepayBillDate(String repayBillDate) {
        this.repayBillDate = repayBillDate;
    }

    public String getRepayBankDate() {
        return repayBankDate;
    }

    public void setRepayBankDate(String repayBankDate) {
        this.repayBankDate = repayBankDate;
    }

    public int getRepayFirstMonthAmount() {
        return repayFirstMonthAmount;
    }

    public void setRepayFirstMonthAmount(int repayFirstMonthAmount) {
        this.repayFirstMonthAmount = repayFirstMonthAmount;
    }

    public String getRepayFirstMonthDatetime() {
        return repayFirstMonthDatetime;
    }

    public void setRepayFirstMonthDatetime(String repayFirstMonthDatetime) {
        this.repayFirstMonthDatetime = repayFirstMonthDatetime;
    }

    public int getRepayMonthAmount() {
        return repayMonthAmount;
    }

    public void setRepayMonthAmount(int repayMonthAmount) {
        this.repayMonthAmount = repayMonthAmount;
    }

    public String getReceiptBankCode() {
        return receiptBankCode;
    }

    public void setReceiptBankCode(String receiptBankCode) {
        this.receiptBankCode = receiptBankCode;
    }

    public String getReceiptBankcardNumber() {
        return receiptBankcardNumber;
    }

    public void setReceiptBankcardNumber(String receiptBankcardNumber) {
        this.receiptBankcardNumber = receiptBankcardNumber;
    }

    public String getReceiptPdf() {
        return receiptPdf;
    }

    public void setReceiptPdf(String receiptPdf) {
        this.receiptPdf = receiptPdf;
    }

    public String getReceiptRemark() {
        return receiptRemark;
    }

    public void setReceiptRemark(String receiptRemark) {
        this.receiptRemark = receiptRemark;
    }

    public String getPledgeDatetime() {
        return pledgeDatetime;
    }

    public void setPledgeDatetime(String pledgeDatetime) {
        this.pledgeDatetime = pledgeDatetime;
    }

    public String getGreenBigSmj() {
        return greenBigSmj;
    }

    public void setGreenBigSmj(String greenBigSmj) {
        this.greenBigSmj = greenBigSmj;
    }

    public String getPledgeBankCommitDatetime() {
        return pledgeBankCommitDatetime;
    }

    public void setPledgeBankCommitDatetime(String pledgeBankCommitDatetime) {
        this.pledgeBankCommitDatetime = pledgeBankCommitDatetime;
    }

    public String getSaleUserId() {
        return saleUserId;
    }

    public void setSaleUserId(String saleUserId) {
        this.saleUserId = saleUserId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public String getCurNodeCode() {
        return curNodeCode;
    }

    public void setCurNodeCode(String curNodeCode) {
        this.curNodeCode = curNodeCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CreditBean getCredit() {
        return credit;
    }

    public void setCredit(CreditBean credit) {
        this.credit = credit;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLoanBankName() {
        return loanBankName;
    }

    public void setLoanBankName(String loanBankName) {
        this.loanBankName = loanBankName;
    }

    public List<BudgetOrderGpsListBean> getBudgetOrderGpsList() {
        return budgetOrderGpsList;
    }

    public void setBudgetOrderGpsList(List<BudgetOrderGpsListBean> budgetOrderGpsList) {
        this.budgetOrderGpsList = budgetOrderGpsList;
    }

    public static class CreditBean {
        /**
         * code : C201806021726455483130
         * loanBankCode : BA201806010714278021343
         * loanAmount : 250000000
         * bizType : 1
         * companyCode : DP201800000000000000001
         * saleUserId : USYS201800000000001
         * applyDatetime : Jun 2, 2018 5:26:45 PM
         * curNodeCode : 001_09
         * creditUserList : [{"code":"CU201806021726455582359","creditCode":"C201806021726455483130","userName":"齐天大圣","relation":"1","loanRole":"1","mobile":"13282838237","idNo":"150404199409090612","idNoFront":"FvOqg19Too_pSXZUkydOQl_OnFWv","idNoReverse":"FvOqg19Too_pSXZUkydOQl_OnFWv","authPdf":"FvOqg19Too_pSXZUkydOQl_OnFWv","interviewPic":"FvOqg19Too_pSXZUkydOQl_OnFWv","bankCreditResultPdf":"FvOqg19Too_pSXZUkydOQl_OnFWv"}]
         */

        private String code;
        private String loanBankCode;
        private int loanAmount;
        private String bizType;
        private String companyCode;
        private String saleUserId;
        private String applyDatetime;
        private String curNodeCode;
        private List<CreditUserListBean> creditUserList;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLoanBankCode() {
            return loanBankCode;
        }

        public void setLoanBankCode(String loanBankCode) {
            this.loanBankCode = loanBankCode;
        }

        public int getLoanAmount() {
            return loanAmount;
        }

        public void setLoanAmount(int loanAmount) {
            this.loanAmount = loanAmount;
        }

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getSaleUserId() {
            return saleUserId;
        }

        public void setSaleUserId(String saleUserId) {
            this.saleUserId = saleUserId;
        }

        public String getApplyDatetime() {
            return applyDatetime;
        }

        public void setApplyDatetime(String applyDatetime) {
            this.applyDatetime = applyDatetime;
        }

        public String getCurNodeCode() {
            return curNodeCode;
        }

        public void setCurNodeCode(String curNodeCode) {
            this.curNodeCode = curNodeCode;
        }

        public List<CreditUserListBean> getCreditUserList() {
            return creditUserList;
        }

        public void setCreditUserList(List<CreditUserListBean> creditUserList) {
            this.creditUserList = creditUserList;
        }

        public static class CreditUserListBean {
            /**
             * code : CU201806021726455582359
             * creditCode : C201806021726455483130
             * userName : 齐天大圣
             * relation : 1
             * loanRole : 1
             * mobile : 13282838237
             * idNo : 150404199409090612
             * idNoFront : FvOqg19Too_pSXZUkydOQl_OnFWv
             * idNoReverse : FvOqg19Too_pSXZUkydOQl_OnFWv
             * authPdf : FvOqg19Too_pSXZUkydOQl_OnFWv
             * interviewPic : FvOqg19Too_pSXZUkydOQl_OnFWv
             * bankCreditResultPdf : FvOqg19Too_pSXZUkydOQl_OnFWv
             */

            private String code;
            private String creditCode;
            private String userName;
            private String relation;
            private String loanRole;
            private String mobile;
            private String idNo;
            private String idNoFront;
            private String idNoReverse;
            private String authPdf;
            private String interviewPic;
            private String bankCreditResultPdf;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreditCode() {
                return creditCode;
            }

            public void setCreditCode(String creditCode) {
                this.creditCode = creditCode;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getRelation() {
                return relation;
            }

            public void setRelation(String relation) {
                this.relation = relation;
            }

            public String getLoanRole() {
                return loanRole;
            }

            public void setLoanRole(String loanRole) {
                this.loanRole = loanRole;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIdNo() {
                return idNo;
            }

            public void setIdNo(String idNo) {
                this.idNo = idNo;
            }

            public String getIdNoFront() {
                return idNoFront;
            }

            public void setIdNoFront(String idNoFront) {
                this.idNoFront = idNoFront;
            }

            public String getIdNoReverse() {
                return idNoReverse;
            }

            public void setIdNoReverse(String idNoReverse) {
                this.idNoReverse = idNoReverse;
            }

            public String getAuthPdf() {
                return authPdf;
            }

            public void setAuthPdf(String authPdf) {
                this.authPdf = authPdf;
            }

            public String getInterviewPic() {
                return interviewPic;
            }

            public void setInterviewPic(String interviewPic) {
                this.interviewPic = interviewPic;
            }

            public String getBankCreditResultPdf() {
                return bankCreditResultPdf;
            }

            public void setBankCreditResultPdf(String bankCreditResultPdf) {
                this.bankCreditResultPdf = bankCreditResultPdf;
            }
        }
    }

    public static class BudgetOrderGpsListBean {
        /**
         * code : GPSAZ201806051451029871331
         * gpsDevNo : G0005
         * gpsType : 1
         * azLocation : 位置
         * azDatetime : 2018-06-05 00:00:00.0
         * azUser : 人员
         * remark : 备注
         * budgetOrder : BO201806050154003632973
         */

        private String code;
        private String gpsDevNo;
        private String gpsType;
        private String azLocation;
        private String azDatetime;
        private String azUser;
        private String remark;
        private String budgetOrder;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getGpsDevNo() {
            return gpsDevNo;
        }

        public void setGpsDevNo(String gpsDevNo) {
            this.gpsDevNo = gpsDevNo;
        }

        public String getGpsType() {
            return gpsType;
        }

        public void setGpsType(String gpsType) {
            this.gpsType = gpsType;
        }

        public String getAzLocation() {
            return azLocation;
        }

        public void setAzLocation(String azLocation) {
            this.azLocation = azLocation;
        }

        public String getAzDatetime() {
            return azDatetime;
        }

        public void setAzDatetime(String azDatetime) {
            this.azDatetime = azDatetime;
        }

        public String getAzUser() {
            return azUser;
        }

        public void setAzUser(String azUser) {
            this.azUser = azUser;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getBudgetOrder() {
            return budgetOrder;
        }

        public void setBudgetOrder(String budgetOrder) {
            this.budgetOrder = budgetOrder;
        }
    }
}
