package net.chinahrd.entity.dto.pc.talentProfitLoss;


public class TalentProfitLossJSONDto implements Comparable<TalentProfitLossJSONDto> {

	private String alias; //
	private boolean isExecutiveSeq; //
	private boolean isManagerSeq; //
	private String jobTitleId; //
	private String lvName; //
	private String name; //
	private String percent; //
	private double percentatge; //
	private String posJobLvId; //
	private boolean selected; //
	private String seqId; //
	private int showIndex; //
	private int sum; //
	private String titleName; //
	private int total; //
	private String value; //

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean getIsExecutiveSeq() {
		return isExecutiveSeq;
	}

	public void setIsExecutiveSeq(boolean isExecutiveSeq) {
		this.isExecutiveSeq = isExecutiveSeq;
	}

	public boolean getIsManagerSeq() {
		return isManagerSeq;
	}

	public void setIsManagerSeq(boolean isManagerSeq) {
		this.isManagerSeq = isManagerSeq;
	}

	public String getJobTitleId() {
		return jobTitleId;
	}

	public void setJobTitleId(String jobTitleId) {
		this.jobTitleId = jobTitleId;
	}

	public String getLvName() {
		return lvName;
	}

	public void setLvName(String lvName) {
		this.lvName = lvName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public double getPercentatge() {
		return percentatge;
	}

	public void setPercentatge(double percentatge) {
		this.percentatge = percentatge;
	}

	public String getPosJobLvId() {
		return posJobLvId;
	}

	public void setPosJobLvId(String posJobLvId) {
		this.posJobLvId = posJobLvId;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public int getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(int showIndex) {
		this.showIndex = showIndex;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(TalentProfitLossJSONDto o) {
		if (!percent.equals(o.getPercent())) {
			return (int) (Double.parseDouble(o.getPercent()) * 100) - (int) (Double.parseDouble(percent) * 100);
		} else {
			return Integer.parseInt(seqId) - Integer.parseInt(o.getSeqId());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TalentProfitLossJSONDto) {
			TalentProfitLossJSONDto dto = (TalentProfitLossJSONDto) obj;
			if (percent.equals(dto.getPercent())) {
				return true;
			} else
				return true;
		} else {
			return false;
		}
	}

}
