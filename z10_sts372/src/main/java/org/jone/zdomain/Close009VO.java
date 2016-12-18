package org.jone.zdomain;
//...과제25_1.A. SQL SELECT문 추가하고, 조회결과를 받을 VO 객체 생성하기.
import java.util.Date;

public class Close009VO {

	private String BE_NM; //...소속
	private String CUR_TEACHER; //...사번
	private String TEACHER_NM; //...교사
	private String POSI_CD_TEXT; //...직책	
	private String CLASS_NM; //...교실
	private String VISIT_CD; //...방문코드
	private String VISIT_CD_NM; //...요일
	
	private String CD_TP_LEVEL_H; //...7...한자
	private String CD_TP_LEVEL_CH; //...8...중국어
	private String CD_TP_LEVEL_B; //...9...책아이
	private String CD_TP_LEVEL_BN; //...10...신_책아이
	private String CD_TP_LEVEL_M; //...11...맞춤
	private String CD_TP_LEVEL_MI; //...12...교과
	private String CD_TP_LEVEL_K; //...13...국어
	private String CD_TP_LEVEL_E; //...14...영어
	private String CD_TP_LEVEL_BI; //...15...북천지
	private String CD_TP_LEVEL_JP; //...16...일본어
	private String CD_TP_LEVEL_HG; //...17...장원급제 
	private String CD_TP_LEVEL_EP; //...18...세이펜영어
	private String CD_TP_LEVEL_NH; //...19...장원한국사
	private String CD_TP_LEVEL_EL; //...20...화상영어
	private String ITEM_CD; //...21...구좌수
	private String MEMBER_ID; //...22...회원수
	private String HS_NUM; //...23...가구수
	public String getBE_NM() {
		return BE_NM;
	}
	public void setBE_NM(String bE_NM) {
		BE_NM = bE_NM;
	}
	public String getCUR_TEACHER() {
		return CUR_TEACHER;
	}
	public void setCUR_TEACHER(String cUR_TEACHER) {
		CUR_TEACHER = cUR_TEACHER;
	}
	public String getTEACHER_NM() {
		return TEACHER_NM;
	}
	public void setTEACHER_NM(String tEACHER_NM) {
		TEACHER_NM = tEACHER_NM;
	}
	public String getPOSI_CD_TEXT() {
		return POSI_CD_TEXT;
	}
	public void setPOSI_CD_TEXT(String pOSI_CD_TEXT) {
		POSI_CD_TEXT = pOSI_CD_TEXT;
	}
	public String getCLASS_NM() {
		return CLASS_NM;
	}
	public void setCLASS_NM(String cLASS_NM) {
		CLASS_NM = cLASS_NM;
	}
	public String getVISIT_CD() {
		return VISIT_CD;
	}
	public void setVISIT_CD(String vISIT_CD) {
		VISIT_CD = vISIT_CD;
	}
	public String getVISIT_CD_NM() {
		return VISIT_CD_NM;
	}
	public void setVISIT_CD_NM(String vISIT_CD_NM) {
		VISIT_CD_NM = vISIT_CD_NM;
	}
	public String getCD_TP_LEVEL_H() {
		return CD_TP_LEVEL_H;
	}
	public void setCD_TP_LEVEL_H(String cD_TP_LEVEL_H) {
		CD_TP_LEVEL_H = cD_TP_LEVEL_H;
	}
	public String getCD_TP_LEVEL_CH() {
		return CD_TP_LEVEL_CH;
	}
	public void setCD_TP_LEVEL_CH(String cD_TP_LEVEL_CH) {
		CD_TP_LEVEL_CH = cD_TP_LEVEL_CH;
	}
	public String getCD_TP_LEVEL_B() {
		return CD_TP_LEVEL_B;
	}
	public void setCD_TP_LEVEL_B(String cD_TP_LEVEL_B) {
		CD_TP_LEVEL_B = cD_TP_LEVEL_B;
	}
	public String getCD_TP_LEVEL_BN() {
		return CD_TP_LEVEL_BN;
	}
	public void setCD_TP_LEVEL_BN(String cD_TP_LEVEL_BN) {
		CD_TP_LEVEL_BN = cD_TP_LEVEL_BN;
	}
	public String getCD_TP_LEVEL_M() {
		return CD_TP_LEVEL_M;
	}
	public void setCD_TP_LEVEL_M(String cD_TP_LEVEL_M) {
		CD_TP_LEVEL_M = cD_TP_LEVEL_M;
	}
	public String getCD_TP_LEVEL_MI() {
		return CD_TP_LEVEL_MI;
	}
	public void setCD_TP_LEVEL_MI(String cD_TP_LEVEL_MI) {
		CD_TP_LEVEL_MI = cD_TP_LEVEL_MI;
	}
	public String getCD_TP_LEVEL_K() {
		return CD_TP_LEVEL_K;
	}
	public void setCD_TP_LEVEL_K(String cD_TP_LEVEL_K) {
		CD_TP_LEVEL_K = cD_TP_LEVEL_K;
	}
	public String getCD_TP_LEVEL_E() {
		return CD_TP_LEVEL_E;
	}
	public void setCD_TP_LEVEL_E(String cD_TP_LEVEL_E) {
		CD_TP_LEVEL_E = cD_TP_LEVEL_E;
	}
	public String getCD_TP_LEVEL_BI() {
		return CD_TP_LEVEL_BI;
	}
	public void setCD_TP_LEVEL_BI(String cD_TP_LEVEL_BI) {
		CD_TP_LEVEL_BI = cD_TP_LEVEL_BI;
	}
	public String getCD_TP_LEVEL_JP() {
		return CD_TP_LEVEL_JP;
	}
	public void setCD_TP_LEVEL_JP(String cD_TP_LEVEL_JP) {
		CD_TP_LEVEL_JP = cD_TP_LEVEL_JP;
	}
	public String getCD_TP_LEVEL_HG() {
		return CD_TP_LEVEL_HG;
	}
	public void setCD_TP_LEVEL_HG(String cD_TP_LEVEL_HG) {
		CD_TP_LEVEL_HG = cD_TP_LEVEL_HG;
	}
	public String getCD_TP_LEVEL_EP() {
		return CD_TP_LEVEL_EP;
	}
	public void setCD_TP_LEVEL_EP(String cD_TP_LEVEL_EP) {
		CD_TP_LEVEL_EP = cD_TP_LEVEL_EP;
	}
	public String getCD_TP_LEVEL_NH() {
		return CD_TP_LEVEL_NH;
	}
	public void setCD_TP_LEVEL_NH(String cD_TP_LEVEL_NH) {
		CD_TP_LEVEL_NH = cD_TP_LEVEL_NH;
	}
	public String getCD_TP_LEVEL_EL() {
		return CD_TP_LEVEL_EL;
	}
	public void setCD_TP_LEVEL_EL(String cD_TP_LEVEL_EL) {
		CD_TP_LEVEL_EL = cD_TP_LEVEL_EL;
	}
	public String getITEM_CD() {
		return ITEM_CD;
	}
	public void setITEM_CD(String iTEM_CD) {
		ITEM_CD = iTEM_CD;
	}
	public String getMEMBER_ID() {
		return MEMBER_ID;
	}
	public void setMEMBER_ID(String mEMBER_ID) {
		MEMBER_ID = mEMBER_ID;
	}
	public String getHS_NUM() {
		return HS_NUM;
	}
	public void setHS_NUM(String hS_NUM) {
		HS_NUM = hS_NUM;
	}
	@Override
	public String toString() {
		return "Close009VO [BE_NM=" + BE_NM + ", CUR_TEACHER=" + CUR_TEACHER + ", TEACHER_NM=" + TEACHER_NM
				+ ", POSI_CD_TEXT=" + POSI_CD_TEXT + ", CLASS_NM=" + CLASS_NM + ", VISIT_CD=" + VISIT_CD
				+ ", VISIT_CD_NM=" + VISIT_CD_NM + ", CD_TP_LEVEL_H=" + CD_TP_LEVEL_H + ", CD_TP_LEVEL_CH="
				+ CD_TP_LEVEL_CH + ", CD_TP_LEVEL_B=" + CD_TP_LEVEL_B + ", CD_TP_LEVEL_BN=" + CD_TP_LEVEL_BN
				+ ", CD_TP_LEVEL_M=" + CD_TP_LEVEL_M + ", CD_TP_LEVEL_MI=" + CD_TP_LEVEL_MI + ", CD_TP_LEVEL_K="
				+ CD_TP_LEVEL_K + ", CD_TP_LEVEL_E=" + CD_TP_LEVEL_E + ", CD_TP_LEVEL_BI=" + CD_TP_LEVEL_BI
				+ ", CD_TP_LEVEL_JP=" + CD_TP_LEVEL_JP + ", CD_TP_LEVEL_HG=" + CD_TP_LEVEL_HG + ", CD_TP_LEVEL_EP="
				+ CD_TP_LEVEL_EP + ", CD_TP_LEVEL_NH=" + CD_TP_LEVEL_NH + ", CD_TP_LEVEL_EL=" + CD_TP_LEVEL_EL
				+ ", ITEM_CD=" + ITEM_CD + ", MEMBER_ID=" + MEMBER_ID + ", HS_NUM=" + HS_NUM + "]";
	}	
	
}
