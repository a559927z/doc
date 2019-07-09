
///**
// * 管理者首页Service实现类
// * Created by wqcai on 15/11/11 0011.
// */
//@Service("manageHomeService")
//public class HomeServiceImpl implements HomeService {
//
//    @Autowired
//    private HomeDao manageHomeDao;
//
//
//    @Override
//    public Date findUpdateDate(String customerId) {
//        return manageHomeDao.findUpdateDate(customerId);
//    }
//
//
//    @Override
//    public PaginationDto<EmpBaseInfoDto> queryTeamEmp(String organId, String customerId, PaginationDto pageDto) {
//
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//
//        RowBounds rowBounds = new RowBounds(pageDto.getOffset(), pageDto.getLimit());
//        int count = manageHomeDao.queryTeamEmpCount(subOrganIdList, customerId);
//        pageDto.setRecords(count);
//        List<EmpBaseInfoDto> dtos = manageHomeDao.queryTeamEmp(subOrganIdList, customerId, rowBounds);
//        pageDto.setRows(dtos);
//        return pageDto;
//    }
//
//    @Override
//    public PaginationDto<RemindEmpDto> queryRemindEmp(String organId, String customerId, PaginationDto pageDto) {
//
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//
//        int begin = (int) ArithUtil.mul(ArithUtil.sub(pageDto.getPage(), 1), pageDto.getRow());
//        int end = (int) ArithUtil.mul(pageDto.getPage(), pageDto.getRow());
//        int count = manageHomeDao.queryRemindEmpCount(subOrganIdList, customerId);
//        pageDto.setRecords(count);
//
//        List<RemindEmpDto> dtos = manageHomeDao.queryRemindEmp(subOrganIdList, customerId, begin, end);
//        pageDto.setRows(dtos);
//        return pageDto;
//    }
//
//    @Override
//    public List<RemindEmpDto> queryBirthdayRemind(String organId, Timestamp time, String customerId) {
//
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//
//        return manageHomeDao.queryBirthdayRemind(subOrganIdList, time, customerId);
//    }
//
//    @Override
//    public Map<String, List<RemindEmpDto>> queryAnnualRemind(String organId, Timestamp time, String customerId) {
//
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//        // 从缓存中获取入司周年配置
//        List<Integer> annuals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.SY_ANNUAL);
//        List<RemindEmpDto> annualEmps = manageHomeDao.queryAnnualRemind(subOrganIdList, time, annuals, customerId);
//        Map<String, List<RemindEmpDto>> map = CollectionKit.newMap();
//        for (Integer annual : annuals) {
//            List<RemindEmpDto> empDtos = CollectionKit.newList();
//            for (RemindEmpDto empDto : annualEmps) {
//                if (annual.equals(empDto.getAnnualYear())) {
//                    empDtos.add(empDto);
//                }
//            }
//            map.put(annual.toString(), empDtos);
//        }
//        return map;
//    }
//
//    @Override
//    public GainOfLossDto findGainOfLossInfo(String organId, String customerId) {
//        GainOfLossDto dto = manageHomeDao.findGainOfLossInfo(organId, customerId, DateUtil.getDBNow().split(" ")[0]);
//        if (null == dto) {
//            dto = new GainOfLossDto();
//        }
//
//        List<LossesEmpDto> rs = CollectionKit.newList();
//
//        List<LossesEmpDto> empDtos = manageHomeDao.queryLossesEmp(organId, customerId, DateUtil.getDBNow());
//
//        if (null != empDtos) {
//            rs = empDtos;
//        }
//
//        List<LossesEmpDto> empInAndOut = manageHomeDao.queryInAndOut(customerId, DateUtil.getDBNow());
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//
//        for (LossesEmpDto empDto : empInAndOut) {
//            if (subOrganIdList.contains(empDto.getInOrganId())
//                    && !(subOrganIdList.contains(empDto.getOutOrganId()))) {
//                // 调入
//                empDto.setOrganizationId(empDto.getInOrganId());
//                empDto.setOrganizationName(empDto.getInOrganName());
//                empDto.setPositionName(empDto.getInPosName());
//                empDto.setSequenceName(empDto.getInSeqName());
//                empDto.setSequenceSubName(empDto.getInSeqSubName());
//                empDto.setAbilityName(empDto.getInAbName());
//                empDto.setJobTitleName(empDto.getInJTName());
//                empDto.setRankName(empDto.getInRName());
//                empDto.setChangeType(empDto.getInCType());
//                empDto.setChangeDate(empDto.getInCDate());
//            }
//            if (subOrganIdList.contains(empDto.getOutOrganId())
//                    && !(subOrganIdList.contains(empDto.getInOrganId()))) {
//                // 调出
//                empDto.setOrganizationId(empDto.getOutOrganId());
//                empDto.setOrganizationName(empDto.getOutOrganName());
//                empDto.setPositionName(empDto.getOutPosName());
//                empDto.setSequenceName(empDto.getOutSeqName());
//                empDto.setSequenceSubName(empDto.getOutSeqSubName());
//                empDto.setAbilityName(empDto.getOutAbName());
//                empDto.setJobTitleName(empDto.getOutJTName());
//                empDto.setRankName(empDto.getOutRName());
//                empDto.setChangeType(empDto.getOutCType());
//                empDto.setChangeDate(empDto.getOutCDate());
//            }
//            rs.add(empDto);
//        }
//
//        //招聘进程
//        List<GainOfLossDto> RecruitmentProcess = manageHomeDao.queryRecruitmentProcess(organId, customerId);
//        if (RecruitmentProcess != null) {
//            GainOfLossDto god = (GainOfLossDto) RecruitmentProcess.get(0);
//            dto.setPubliceJobNum(god.getPubliceJobNum());
//            dto.setResumeNum(god.getResumeNum());
//            dto.setAcceptNum(god.getAcceptNum());
//            dto.setOfferNum(god.getOfferNum());
//        }
//
//        if (null == empDtos) {
//            empDtos = new ArrayList<LossesEmpDto>();
//        }
//        dto.setEmpDtos(empDtos);
//
//        return dto;
//    }
//
//    @Override
//    public Map<Integer, Object> getTeamImgAb(String organId, String customerId) {
//
//   
//       ApiVaild< List<TeamImgEmpDto>> apiVaild= TeamImgApi.queryTeamImgAb.get(organId, customerId);
//    	List<TeamImgEmpDto> rs=apiVaild.getResult();
//       if (!apiVaild.isValid()) {
//           rs = new ArrayList<TeamImgEmpDto>();
//       }
//        // 分母不会为null
//        double total = rs.size();
//
//        Map<Integer, Object> rsMap = CollectionKit.newMap();
//        rsMap.put(0, packagSexPer(rs, total));    //性别占比
//        rsMap.put(1, packagYears(rs, total));        //出生年度占比
//        rsMap.put(2, packagMarryPer(rs, total));    //婚姻状况占比
//        rsMap.put(3, packagAbility(rs, customerId));        //能力层级占比
//        rsMap.put(4, rs.size());    //总人数
//        return rsMap;
//    }
//
//    /**
//     * 性别占比
//     *
//     * @param rs
//     * @param total
//     * @return
//     */
//    private KVItemDto packagSexPer(List<TeamImgEmpDto> rs, double total) {
//        if (total == 0) {
//            KVItemDto dto = new KVItemDto();
//            dto.setK("sex");
//            dto.setV(null);
//            return dto;
//        }
//        double manTotal = 0.0, womanTotal = 0.0;
//        double manPer = 0.0, womanPer = 0.0;
//        MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
//        for (TeamImgEmpDto dto : rs) {
//            m.add(dto.getSex(), dto);
//        }
//        for (Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
//            String key = empEntry.getKey();
//            //男
//            if (key.equals("m")) {
//                manTotal = empEntry.getValue().size();
//                manPer = ArithUtil.div(manTotal, total, 2);
//            } else {
//                womanTotal = empEntry.getValue().size();
//                womanPer = ArithUtil.div(womanTotal, total, 2);
//            }
//        }
//        KVItemDto dto = new KVItemDto();
//        if (manTotal > womanTotal) {
//            dto.setK("男士");
//            dto.setV(String.valueOf((int) ArithUtil.mul(manPer, 100.0)));
//        } else if (manTotal < womanTotal) {
//            dto.setK("女士");
//            dto.setV(String.valueOf((int) ArithUtil.mul(womanPer, 100.0)));
//        } else {
//            dto.setK("sex");
//            dto.setV(null);
//        }
//        return dto;
//    }
//
//    /**
//     * 出生日期占比
//     *
//     * @param rs
//     * @param total
//     * @return
//     */
//    private KVItemDto packagYears(List<TeamImgEmpDto> rs, double total) {
//        if (total == 0) {
//            KVItemDto dto = new KVItemDto();
//            dto.setK("没有数据");
//            dto.setV(null);
//            return dto;
//        }
//        MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
//        for (TeamImgEmpDto dto : rs) {
//            DateTime dt = new DateTime(dto.getBirthDate());
//            int year = dt.getYear();
//            String years = "";
//            if (year >= 1920 && year < 1930) {
//                years = "20后";
//            } else if (year >= 1930 && year < 1940) {
//                years = "30后";
//            } else if (year >= 1940 && year < 1950) {
//                years = "40后";
//            } else if (year >= 1950 && year < 1960) {
//                years = "50后";
//            } else if (year >= 1960 && year < 1970) {
//                years = "60后";
//            } else if (year >= 1970 && year < 1980) {
//                years = "70后";
//            } else if (year >= 1980 && year < 1990) {
//                years = "80后";
//            } else if (year >= 1990 && year < 2000) {
//                years = "90后";
//            } else if (year >= 2000 && year < 2010) {
//                years = "00后";
//            } else if (year >= 2010 && year < 2020) {
//                years = "10后";
//            } else {
//                years = "新新代";
//            }
//            m.add(years, dto);
//        }
//        int maxSize = 0;
//        String years = "";
//        int maxCount = 0;
//        for (Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
//            Integer size = empEntry.getValue().size();
//            // 年份最多人
//            if (maxSize < size) {
//                maxCount = 1;
//                maxSize = size;
//                years = empEntry.getKey();
//            } else if (maxSize == size) {
//                maxCount++;
//            }
//        }
//        if (maxCount > 1) {
//            KVItemDto dto = new KVItemDto();
//            dto.setK("years");
//            dto.setV(null);
//            return dto;
//        }
//        double maxPer = ArithUtil.div(maxSize, total, 2);
//        KVItemDto dto = new KVItemDto();
//        dto.setK(years);
//        dto.setV(String.valueOf((int) ArithUtil.mul(maxPer, 100.0)));
//        return dto;
//    }
//
//    /**
//     * 婚姻占比
//     *
//     * @param rs
//     * @param total
//     * @return
//     */
//    private KVItemDto packagMarryPer(List<TeamImgEmpDto> rs, double total) {
//        if (total == 0) {
//            KVItemDto dto = new KVItemDto();
//            dto.setK("marry");
//            dto.setV(null);
//            return dto;
//        }
//        double unMarry = 0.0, marry = 0.0;
//        double unMarryPer = 0.0, marryPer = 0.0;
//        MultiValueMap<Integer, TeamImgEmpDto> m = new LinkedMultiValueMap<Integer, TeamImgEmpDto>();
//        for (TeamImgEmpDto dto : rs) {
//            if (dto.getMarryStatus() != null) {
//                m.add(dto.getMarryStatus(), dto);
//            }
//        }
//        for (Entry<Integer, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
//            // 未婚
//            if (empEntry.getKey() == 0) {
//                unMarry = empEntry.getValue().size();
//                unMarryPer = ArithUtil.div(unMarry, total, 2);
//            } else {
//                marry = empEntry.getValue().size();
//                marryPer = ArithUtil.div(marry, total, 2);
//            }
//        }
//        KVItemDto dto = new KVItemDto();
//        if (unMarry > marry) {
//            dto.setK("未婚");
//            dto.setV(String.valueOf((int) ArithUtil.mul(unMarryPer, 100.0)));
//        } else if (unMarry < marry) {
//            dto.setK("已婚");
//            dto.setV(String.valueOf((int) ArithUtil.mul(marryPer, 100.0)));
//        } else {
//            dto.setK("marry");
//            dto.setV(null);
//        }
//        return dto;
//    }
//
//    /**
//     * 各个职位层级有多少人
//     *
//     * @param rs
//     * @param customerId
//     * @return
//     */
//    private List<KVItemDto> packagAbility(List<TeamImgEmpDto> rs, String customerId) {
//        List<KVItemDto> dtos = CollectionKit.newList();
//        MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
//        for (TeamImgEmpDto dto : rs) {
//            String ability = dto.getAbility();
//            if (ability == null) {
//                continue;
//            }
//            m.add(ability, dto);
//        }
//        List<KVItemDto> abilityList = SysCache.getInstance().ability.get();
//        int abilityLen = abilityList.size();
//        double scale = ArithUtil.div(100.0, abilityLen, 2);
//
//        for (int i = 0; i < abilityLen; i++) {
//            String abilityCurtName = abilityList.get(i).getV();
//            boolean hasBool = false;
//            int abilityDuty = i + 1;
//            for (Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
//                if (empEntry.getKey().equals(abilityCurtName)) {
//                    KVItemDto kvItem = new KVItemDto();
//                    kvItem.setK(String.valueOf((int) ArithUtil.mul(scale, abilityDuty)));
//                    kvItem.setV(abilityCurtName + "级，" + empEntry.getValue().size() + "人");
//                    dtos.add(kvItem);
//                    hasBool = true;
//                    continue;
//                }
//            }
//            if (!hasBool) {
//                KVItemDto kvItem = new KVItemDto();
//                kvItem.setK(String.valueOf((int) ArithUtil.mul(scale, abilityDuty)));
//                kvItem.setV(abilityCurtName + "级，0人");
//                dtos.add(kvItem);
//            }
//        }
//        return dtos;
//    }
//
//    @Override
//    public Map<Integer, Object> getPerformance(String organId, String customerId) {
//    	List<PerformanceDto> rs = null;
//    	List<PerformanceEmpDto> rsEmp=null;
//    	ApiVaild<List<PerformanceDto>> rsVaild=EmployeePerformanceApi.queryPerformance.get(organId, customerId);
//        if(rsVaild.isValid()){
//        	rs=rsVaild.getResult();
//        }
//        
//        ApiVaild<List<PerformanceEmpDto>> rsEmpVaild=EmployeePerformanceApi.queryPerformanceEmp.get(organId, customerId);
//        if(rsEmpVaild.isValid()){
//        	rsEmp=rsEmpVaild.getResult();
//        }
//        Map<Integer, Object> rsMap = CollectionKit.newMap();
//        rsMap.put(0, rs);
//        rsMap.put(1, rsEmp);
//        return rsMap;
//    }
//
//    /**
//     * 员工近两周加班信息
//     */
//    public List<WorkOvertimeDto> getWorkOvertimeInfo(String empid, String customerId) {
//        Integer cycle = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_REVIEWOTWEEK);    // 考察加班周期时间key
//        //SY_OTTIME
//        List<WorkOvertimeDto> rs = manageHomeDao.queryWorkOvertimeInfo(empid, customerId, DateUtil.getDBCurdate(), cycle);
//        return rs;
//    }
//
//
////    public List<WarnSynopsisDto> getWarnInfo(String organId, String customerId) {
////        List<WarnSynopsisDto> rs = manageHomeDao.queryWarnInfo(organId, customerId);
////        return rs;
////    }
//
//    @Override
//    public List<WarnInfoDto> queryLowPerformanceEmp(String organId, String customerId) {
//        // 从缓存获取考察绩效次数
//        Map<String, Object> map = CollectionKit.newMap();
//        Double reviewPerfman = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.SY_REVIEWPERFMAN);    // 考察绩效次数key
//        List<Integer> listRang = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.SY_LOWPERFMAN);
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//
//        map.put("customerId", customerId);
//        map.put("subOrganIdList", subOrganIdList);
//        map.put("reviewPerfman", reviewPerfman);
//        map.putAll(getFormanceRange(listRang));
//        map.putAll(getDate(reviewPerfman));
//        List<WarnInfoDto> rs = manageHomeDao.queryLowPerformanceEmp(map);
//        return rs;
//    }
//
//    @Override
//    public List<WarnInfoDto> queryHighPerformanceEmp(String organId, String customerId) {
//        Map<String, Object> map = CollectionKit.newMap();
//        Double reviewPerfman = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.SY_REVIEWPERFMAN);    // 考察绩效次数key
//        List<Integer> listRang = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.SY_HEIGHTPERFMAN);
//        //  String[] time = getDate(reviewPerfman);
//        // 从缓存获取
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//
//        map.put("customerId", customerId);
//        map.put("subOrganIdList", subOrganIdList);
//        map.put("reviewPerfman", reviewPerfman);
//        map.putAll(getFormanceRange(listRang));
//        map.putAll(getDate(reviewPerfman));
//        List<WarnInfoDto> rs = manageHomeDao.queryHighPerformanceEmp(map);
//
//        return rs;
//    }
//
//    @Override
//    public List<WarnInfoDto> queryRunOffWarnEmp(String customerId,
//                                                String organId) {
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//
//        List<WarnInfoDto> rs = manageHomeDao.queryRunOffWarnEmp(customerId, subOrganIdList);
//        return rs;
//    }
//
//    @Override
//    public Integer queryRunOffWarnCount(String customerId, String organId) {
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//
//        Integer count = manageHomeDao.queryRunOffWarnCount(customerId, subOrganIdList);
//        return count;
//    }
//
//    @Override
//    public List<WarnInfoDto> queryOvertimeEmp(String customerId,
//                                              String organId) {
//        // 从缓存获取有效工作日
////        int availabilityDay = TimeBuffer.getInstance().AvailabilityDayNum.get(DateUtil.getDBCurdate(), PropertiesUtil.getProperty(ConfigKeyUtil.SY_REVIEWOTWEEK));
//    	 int availabilityDay=0;
//    	// 从缓存获取考察加班周期
//        Integer cycle = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_REVIEWOTWEEK);
//        // 从缓存获取加班时长预警
//        double warnTime = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.SY_OTTIME);
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//
//        List<WarnInfoDto> rs = manageHomeDao.queryOvertimeEmp(customerId, subOrganIdList, availabilityDay, cycle, warnTime, DateUtil.getDBCurdate());
//        return rs;
//    }
//
//
//    @Override
//    public List<TalentDevelEmpDto> getTalentDevelExamItem(Integer yearInt, String empId, String customerId) {
//        List<TalentDevelEmpDto> rs = manageHomeDao.queryTalentDevelExamItemData(yearInt, empId, customerId);
//        return rs;
//    }
//
//
//    /**
//     * 管理者首页-人才发展
//     *
//     * @param organId
//     * @param customerId
//     * @return
//     */
//    @Override
//    public Map<Integer, Object> getTalentDevel(String organId, String customerId) {
//
//        // 从缓存获取所有子孙机构ID
//        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
//        // 测评最新时间
//        int year = manageHomeDao.queryReportMaxYear(customerId);
//        // 360测评data
//        List<TalentDevelEmpDto> rs = manageHomeDao.queryTalentDevel(subOrganIdList, customerId, year);
//
//        // 晋升数据
//        List<TalentDevelEmpDto> promotionData = manageHomeDao.queryTalentDevelPromotionData(subOrganIdList, customerId);
//
//        // 培训数据
//        List<TalentDevelEmpDto> trainData = manageHomeDao.queryTalentDevelTrainData(subOrganIdList, customerId);
//
//        // 测评数据
//        List<TalentDevelEmpDto> examData = manageHomeDao.queryTalentDevelExamData(subOrganIdList, customerId, year);
//
//
//        Map<Integer, Object> map = CollectionKit.newMap();
//        Integer packTotal = packTotal(rs);
//        List<TalentDevelEmpDto> packAvgValueDto = packAvgValue(rs);
//
//        Integer quartPromoNum = 0;
//        Double promoMonths = 0.0;
//        Integer trainPerpol = 0;
//        Double trainPerpolAvg = 0.0;
//        if (null != promotionData) {
//            quartPromoNum = packQuarterPromo(promotionData);
//            promoMonths = packAvgPromoDate(promotionData);
//        }
//        if (null != trainData) {
//            trainPerpol = trainData.size();
//            trainPerpolAvg = packAvgTrainPerpol(trainData);
//        }
//
//
//        // 为了页面数组由0开始，map.key为0开始
//        map.put(0, packTotal);
//        map.put(1, packAvgValueDto);
//
//        // 4个pie data
//        map.put(2, quartPromoNum);
//        map.put(3, promoMonths);
//        map.put(4, trainPerpol);
//        map.put(5, trainPerpolAvg);
//
//        // 3个grid data
//        map.put(6, promotionData);
//        map.put(7, trainData);
//        map.put(8, examData);
//
//        /**
//         * 测评最新时间
//         */
//        map.put(9, year);
//        return map;
//    }
//
//    /**
//     * 我的团队最近一次参加360测评总人数
//     *
//     * @param rs
//     * @return
//     */
//    private Integer packTotal(List<TalentDevelEmpDto> rs) {
//
//        if (null == rs) {
//            return null;
//        }
//        List<String> rsList = CollectionKit.newList();
//        for (TalentDevelEmpDto dto : rs) {
//            String empId1 = dto.getEmpId();
//
//            if (rsList.size() == 0) {
//                rsList.add(empId1);
//            }
//            if (!(rsList.contains(empId1))) {
//                rsList.add(empId1);
//            }
//
//        }
//        return rsList.size();
//    }
//
//    /**
//     * 计算同一测评项目里的：平均得分、参考人数
//     *
//     * @param rs
//     * @return
//     */
//    private List<TalentDevelEmpDto> packAvgValue(List<TalentDevelEmpDto> rs) {
//        MultiValueMap<String, TalentDevelEmpDto> m = new LinkedMultiValueMap<String, TalentDevelEmpDto>();
//        for (TalentDevelEmpDto dto : rs) {
//            if (dto.getAbilityName() != null) {
//                m.add(dto.getAbilityName(), dto);
//            }
//        }
//        List<TalentDevelEmpDto> avgScoreDtos = CollectionKit.newList();
//        Double gainScore = 0.0;
//        Integer abTotal = 0;
//        for (Entry<String, List<TalentDevelEmpDto>> empEntry : m.entrySet()) {
//            TalentDevelEmpDto avgScoreDto = new TalentDevelEmpDto();
//            gainScore = 0.0;
//            String mKey = empEntry.getKey();
//            List<TalentDevelEmpDto> mValues = empEntry.getValue();
//            abTotal = mValues.size();
//            // 计算每个项目的总分
//            for (TalentDevelEmpDto dto : mValues) {
//                gainScore += dto.getGainScore();
//            }
//            avgScoreDto.setAbilityName(mKey);
//            avgScoreDto.setGainScoreAvg(ArithUtil.div(gainScore, abTotal, 2));// 计算每个项目的平均分
//            avgScoreDto.setAbTotal(abTotal);
//            avgScoreDtos.add(avgScoreDto);
//        }
//        // 再为之前计算好的平均分的对象带入moduleType属性
//        for (TalentDevelEmpDto dto : avgScoreDtos) {
//            String abilityName = dto.getAbilityName();
//            for (TalentDevelEmpDto soureDto : rs) {
//                if (soureDto.getAbilityName().equals(abilityName)) {
//                    dto.setModuleType(soureDto.getModuleType());
//                    break;
//                }
//            }
//        }
//        return avgScoreDtos;
//    }
//
//    /**
//     * 获取高绩效和低绩效的查询时间
//     *
//     * @return
//     */
//    private Map<String, String> getDate(Double num) {
//        //	int num=4;
//        num = num - 1;
//        int record = 0;
//        Calendar c = Calendar.getInstance();
//        c.setTime(DateUtil.getDate());
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        String startDate = "";
//        String endDate = "";
//        int m = 0;  // 0代表 12  1代表06
//        if (6 < month && month <= 12) {
//            endDate = year + "" + "06";
//            while (num > record) {
//                if (record % 2 == 0) {
//                    year--;
//                    m = 0;
//                } else {
//                    m = 1;
//                }
//                record++;
//            }
//        } else {
//            endDate = (year - 1) + "" + "12";
//            while (num > record) {
//                if (record % 2 != 0) {
//                    year--;
//                    m = 0;
//                } else {
//                    m = 1;
//                }
//                record++;
//            }
//        }
//        startDate = year + "" + (m > 0 ? "06" : "12");
//
//        Map<String, String> map = CollectionKit.newMap();
//        map.put("startDate", startDate);
//        map.put("endDate", endDate);
//        return map;
//    }
//
//    /**
//     * 获取高绩效 低绩效范围
//     *
//     * @param listRang
//     * @return
//     */
//    private Map<String, Integer> getFormanceRange(List<Integer> listRang) {
//        Map<String, Integer> map = CollectionKit.newMap();
//        Integer low = 0;
//        Integer hight = 0;
//        if (listRang != null) {
//            if (listRang.size() == 1) {
////        		low=Integer.valueOf(""+listRang.get(0)) ;
////        		hight=low;
//                low = listRang.get(0);
//                hight = low;
//            } else if (listRang.size() > 1) {
////        		low=Integer.valueOf(""+listRang.get(0)) ;
////        		hight=Integer.valueOf(""+listRang.get(1)) ;
//                low = listRang.get(0);
//                hight = listRang.get(listRang.size() - 1);
//            }
//        }
//        map.put("low", low);
//        map.put("hight", hight);
//        return map;
//    }
//
//    /**
//     * 当季晋升人数
//     *
//     * @param rs
//     * @return
//     */
//    private Integer packQuarterPromo(List<TalentDevelEmpDto> rs) {
//        Integer calRs = 0;
//        calRs = rs.size();
//        return calRs;
//    }
//
//    /**
//     * 姓名		最后一次晋升时间~入职时间		晋升用时		晋升次数
//     * 张三		2014.1.1-2010.2.2			48月			2
//     * 李四 	2015.1.1-2010.1.1			60月			3
//     * 王五		--
//     * 21.6=(48+60)/(2+3)
//     *
//     * @param rs
//     * @return
//     */
//    private Double packAvgPromoDate(List<TalentDevelEmpDto> rs) {
//        Double calRs = 0.0;
//        Double stayTime = 0.0;
//        Integer pomoteNum = 0;
//        for (TalentDevelEmpDto dto : rs) {
//            stayTime += dto.getStayTime();
//        }
//        for (TalentDevelEmpDto dto : rs) {
//            pomoteNum += dto.getPomoteNum();
//        }
//        if (0 < rs.size()) {
//            calRs = ArithUtil.div(stayTime, pomoteNum, 2);
//        }
//        return calRs;
//    }
//
//    /**
//     * 人均培训次数
//     *
//     * @param rs
//     * @return
//     */
//    private Double packAvgTrainPerpol(List<TalentDevelEmpDto> rs) {
//        Integer trainNum = 0;
//        for (TalentDevelEmpDto dto : rs) {
//            trainNum += dto.getTrainNum();
//        }
//        Double calRs = 0.0;
//        if (0 < rs.size()) {
//            calRs = ArithUtil.div(trainNum, rs.size(), 2);
//        }
//        return calRs;
//    }
//
//
//}
package net.chinahrd.homepage.mvc.pc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import net.chinahrd.api.EmployeePerformanceApi;
import net.chinahrd.api.TeamImgApi;
import net.chinahrd.core.api.ApiManagerCenter;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.emp.WorkOvertimeDto;
import net.chinahrd.entity.dto.pc.manage.EmpBaseInfoDto;
import net.chinahrd.entity.dto.pc.manage.GainOfLossDto;
import net.chinahrd.entity.dto.pc.manage.LossesEmpDto;
import net.chinahrd.entity.dto.pc.manage.PerformanceDto;
import net.chinahrd.entity.dto.pc.manage.PerformanceEmpDto;
import net.chinahrd.entity.dto.pc.manage.RemindEmpDto;
import net.chinahrd.entity.dto.pc.manage.TalentDevelEmpDto;
import net.chinahrd.entity.dto.pc.manage.WarnInfoDto;
import net.chinahrd.entity.dto.pc.teamImg.TeamImgEmpDto;
import net.chinahrd.homepage.module.Cache;
import net.chinahrd.homepage.mvc.pc.dao.HomeDao;
import net.chinahrd.homepage.mvc.pc.service.HomeService;
import net.chinahrd.module.SysCache;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.PropertiesUtil;



/**
 * 管理者首页Service实现类
 * Created by wqcai on 15/11/11 0011.
 */
@Service("manageHomeService")
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeDao manageHomeDao;


    @Override
    public Date findUpdateDate(String customerId) {
        return manageHomeDao.findUpdateDate(customerId);
    }


    @Override
    public PaginationDto<EmpBaseInfoDto> queryTeamEmp(String organId, String customerId, PaginationDto pageDto) {

        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

        RowBounds rowBounds = new RowBounds(pageDto.getOffset(), pageDto.getLimit());
        int count = manageHomeDao.queryTeamEmpCount(subOrganIdList, customerId);
        pageDto.setRecords(count);
        List<EmpBaseInfoDto> dtos = manageHomeDao.queryTeamEmp(subOrganIdList, customerId, rowBounds);
        pageDto.setRows(dtos);
        return pageDto;
    }

    @Override
    public PaginationDto<RemindEmpDto> queryRemindEmp(String organId, String customerId, PaginationDto pageDto) {

        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

        int begin = (int) ArithUtil.mul(ArithUtil.sub(pageDto.getPage(), 1), pageDto.getRow());
        int end = (int) ArithUtil.mul(pageDto.getPage(), pageDto.getRow());
        int count = manageHomeDao.queryRemindEmpCount(subOrganIdList, customerId);
        pageDto.setRecords(count);

        List<RemindEmpDto> dtos = manageHomeDao.queryRemindEmp(subOrganIdList, customerId, begin, end);
        pageDto.setRows(dtos);
        return pageDto;
    }

    @Override
    public List<RemindEmpDto> queryBirthdayRemind(String organId, Timestamp time, String customerId) {

        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

        return manageHomeDao.queryBirthdayRemind(subOrganIdList, time, customerId);
    }

    @Override
    public Map<String, List<RemindEmpDto>> queryAnnualRemind(String organId, Timestamp time, String customerId) {

        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        // 从缓存中获取入司周年配置
        List<Integer> annuals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.SY_ANNUAL);
        List<RemindEmpDto> annualEmps = manageHomeDao.queryAnnualRemind(subOrganIdList, time, annuals, customerId);
        Map<String, List<RemindEmpDto>> map = CollectionKit.newMap();
        for (Integer annual : annuals) {
            List<RemindEmpDto> empDtos = CollectionKit.newList();
            for (RemindEmpDto empDto : annualEmps) {
                if (annual.equals(empDto.getAnnualYear())) {
                    empDtos.add(empDto);
                }
            }
            map.put(annual.toString(), empDtos);
        }
        return map;
    }

    @Override
    public GainOfLossDto findGainOfLossInfo(String organId, String customerId) {
        GainOfLossDto dto = manageHomeDao.findGainOfLossInfo(organId, customerId, DateUtil.getDBNow().split(" ")[0]);
        if (null == dto) {
            dto = new GainOfLossDto();
        }

        List<LossesEmpDto> rs = CollectionKit.newList();

        List<LossesEmpDto> empDtos = manageHomeDao.queryLossesEmp(organId, customerId, DateUtil.getDBNow());

        if (null != empDtos) {
            rs = empDtos;
        }

        List<LossesEmpDto> empInAndOut = manageHomeDao.queryInAndOut(customerId, DateUtil.getDBNow());
        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

        for (LossesEmpDto empDto : empInAndOut) {
            if (subOrganIdList.contains(empDto.getInOrganId())
                    && !(subOrganIdList.contains(empDto.getOutOrganId()))) {
                // 调入
                empDto.setOrganizationId(empDto.getInOrganId());
                empDto.setOrganizationName(empDto.getInOrganName());
                empDto.setPositionName(empDto.getInPosName());
                empDto.setSequenceName(empDto.getInSeqName());
                empDto.setSequenceSubName(empDto.getInSeqSubName());
                empDto.setAbilityName(empDto.getInAbName());
                empDto.setJobTitleName(empDto.getInJTName());
                empDto.setRankName(empDto.getInRName());
                empDto.setChangeType(empDto.getInCType());
                empDto.setChangeDate(empDto.getInCDate());
            }
            if (subOrganIdList.contains(empDto.getOutOrganId())
                    && !(subOrganIdList.contains(empDto.getInOrganId()))) {
                // 调出
                empDto.setOrganizationId(empDto.getOutOrganId());
                empDto.setOrganizationName(empDto.getOutOrganName());
                empDto.setPositionName(empDto.getOutPosName());
                empDto.setSequenceName(empDto.getOutSeqName());
                empDto.setSequenceSubName(empDto.getOutSeqSubName());
                empDto.setAbilityName(empDto.getOutAbName());
                empDto.setJobTitleName(empDto.getOutJTName());
                empDto.setRankName(empDto.getOutRName());
                empDto.setChangeType(empDto.getOutCType());
                empDto.setChangeDate(empDto.getOutCDate());
            }
            rs.add(empDto);
        }

        //招聘进程
        List<GainOfLossDto> RecruitmentProcess = manageHomeDao.queryRecruitmentProcess(organId, customerId);
        if (RecruitmentProcess != null&&RecruitmentProcess.size()>0) {
            GainOfLossDto god =  RecruitmentProcess.get(0);
            dto.setPubliceJobNum(god.getPubliceJobNum());
            dto.setResumeNum(god.getResumeNum());
            dto.setAcceptNum(god.getAcceptNum());
            dto.setOfferNum(god.getOfferNum());
        }

        if (null == empDtos) {
            empDtos = new ArrayList<LossesEmpDto>();
        }
        dto.setEmpDtos(empDtos);

        return dto;
    }

    @Override
    public Map<Integer, Object> getTeamImgAb(String organId, String customerId) {

		// 从缓存获取所有子孙机构ID
//		List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

		// ApiVaild< List<TeamImgEmpDto>> apiVaild=
		// TeamImgApi.queryTeamImgAb.get(organId, customerId);
		List<TeamImgEmpDto> rs = null;
		List<KVItemDto> abilityCount = null;
		TeamImgApi api = ApiManagerCenter.getApiDoc(TeamImgApi.class);
		if (null != api) {
			rs = api.queryTeamImgAb(organId, customerId);
			abilityCount = api.groupCountTeamImgAb(organId, customerId);
		}
		// List<TeamImgEmpDto> rs=apiVaild.getResult();
		// if (!apiVaild.isValid()) {
		// rs = new ArrayList<TeamImgEmpDto>();
		// }
		if (CollectionKit.isEmpty(rs)) {
			rs = new ArrayList<TeamImgEmpDto>();
		}
		// 分母不会为null
		double total = rs.size();

        Map<Integer, Object> rsMap = CollectionKit.newMap();
        rsMap.put(0, packagSexPer(rs, total));			//性别占比
        rsMap.put(1, packagYears(rs, total));			//出生年度占比
        rsMap.put(2, packagMarryPer(rs, total)); 		//婚姻状况占比
        rsMap.put(3, packagAbility(abilityCount, customerId));	//能力层级占比
        rsMap.put(4, rs.size());    					//总人数
        return rsMap;
    }

    /**
     * 性别占比
     *
     * @param rs
     * @param total
     * @return
     */
    private KVItemDto packagSexPer(List<TeamImgEmpDto> rs, double total) {
        if (total == 0) {
            KVItemDto dto = new KVItemDto();
            dto.setK("sex");
            dto.setV(null);
            return dto;
        }
        double manTotal = 0.0, womanTotal = 0.0;
        double manPer = 0.0, womanPer = 0.0;
        MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
        for (TeamImgEmpDto dto : rs) {
            m.add(dto.getSex(), dto);
        }
        for (Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
            String key = empEntry.getKey();
            //男
            if (key.equals("m")) {
                manTotal = empEntry.getValue().size();
                manPer = ArithUtil.div(manTotal, total, 2);
            } else {
                womanTotal = empEntry.getValue().size();
                womanPer = ArithUtil.div(womanTotal, total, 2);
            }
        }
        KVItemDto dto = new KVItemDto();
        if (manTotal > womanTotal) {
            dto.setK("男士");
            dto.setV(String.valueOf((int) ArithUtil.mul(manPer, 100.0)));
        } else if (manTotal < womanTotal) {
            dto.setK("女士");
            dto.setV(String.valueOf((int) ArithUtil.mul(womanPer, 100.0)));
        } else {
            dto.setK("sex");
            dto.setV(null);
        }
        return dto;
    }

    /**
     * 出生日期占比
     *
     * @param rs
     * @param total
     * @return
     */
    private KVItemDto packagYears(List<TeamImgEmpDto> rs, double total) {
        if (total == 0) {
            KVItemDto dto = new KVItemDto();
            dto.setK("没有数据");
            dto.setV(null);
            return dto;
        }
        MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
        for (TeamImgEmpDto dto : rs) {
            DateTime dt = new DateTime(dto.getBirthDate());
            int year = dt.getYear();
            String years = "";
            if (year >= 1920 && year < 1930) {
                years = "20后";
            } else if (year >= 1930 && year < 1940) {
                years = "30后";
            } else if (year >= 1940 && year < 1950) {
                years = "40后";
            } else if (year >= 1950 && year < 1960) {
                years = "50后";
            } else if (year >= 1960 && year < 1970) {
                years = "60后";
            } else if (year >= 1970 && year < 1980) {
                years = "70后";
            } else if (year >= 1980 && year < 1990) {
                years = "80后";
            } else if (year >= 1990 && year < 2000) {
                years = "90后";
            } else if (year >= 2000 && year < 2010) {
                years = "00后";
            } else if (year >= 2010 && year < 2020) {
                years = "10后";
            } else {
                years = "新新代";
            }
            m.add(years, dto);
        }
        int maxSize = 0;
        String years = "";
        int maxCount = 0;
        for (Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
            Integer size = empEntry.getValue().size();
            // 年份最多人
            if (maxSize < size) {
                maxCount = 1;
                maxSize = size;
                years = empEntry.getKey();
            } else if (maxSize == size) {
                maxCount++;
            }
        }
        if (maxCount > 1) {
            KVItemDto dto = new KVItemDto();
            dto.setK("years");
            dto.setV(null);
            return dto;
        }
        double maxPer = ArithUtil.div(maxSize, total, 2);
        KVItemDto dto = new KVItemDto();
        dto.setK(years);
        dto.setV(String.valueOf((int) ArithUtil.mul(maxPer, 100.0)));
        return dto;
    }

    /**
     * 婚姻占比
     *
     * @param rs
     * @param total
     * @return
     */
    private KVItemDto packagMarryPer(List<TeamImgEmpDto> rs, double total) {
        if (total == 0) {
            KVItemDto dto = new KVItemDto();
            dto.setK("marry");
            dto.setV(null);
            return dto;
        }
        double unMarry = 0.0, marry = 0.0;
        double unMarryPer = 0.0, marryPer = 0.0;
        MultiValueMap<Integer, TeamImgEmpDto> m = new LinkedMultiValueMap<Integer, TeamImgEmpDto>();
        for (TeamImgEmpDto dto : rs) {
            if (dto.getMarryStatus() != null) {
                m.add(dto.getMarryStatus(), dto);
            }
        }
        for (Entry<Integer, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
            // 未婚
            if (empEntry.getKey() == 0) {
                unMarry = empEntry.getValue().size();
                unMarryPer = ArithUtil.div(unMarry, total, 2);
            } else {
                marry = empEntry.getValue().size();
                marryPer = ArithUtil.div(marry, total, 2);
            }
        }
        KVItemDto dto = new KVItemDto();
        if (unMarry > marry) {
            dto.setK("未婚");
            dto.setV(String.valueOf((int) ArithUtil.mul(unMarryPer, 100.0)));
        } else if (unMarry < marry) {
            dto.setK("已婚");
            dto.setV(String.valueOf((int) ArithUtil.mul(marryPer, 100.0)));
        } else {
            dto.setK("marry");
            dto.setV(null);
        }
        return dto;
    }

    /**
     * 各个职位层级有多少人
     *
     * @param rs
     * @param customerId
     * @return
     */
    private List<KVItemDto> packagAbility2(List<TeamImgEmpDto> rs, String customerId) {
        List<KVItemDto> dtos = CollectionKit.newList();
        MultiValueMap<String, TeamImgEmpDto> m = new LinkedMultiValueMap<String, TeamImgEmpDto>();
        for (TeamImgEmpDto dto : rs) {
            String ability = dto.getAbility();
            if (ability == null) {
                continue;
            }
            m.add(ability, dto);
        }
//        List<KVItemDto> abilityList = SysCache.getInstance().getAbility.get();
        List<KVItemDto> abilityList = CacheHelper.getAbility();
//        List<KVItemDto> abilityList = manageHomeDao.queryTeamImgAbility(customerId);
        int abilityLen = abilityList.size();
        double scale = ArithUtil.div(100.0, abilityLen, 2);

        for (int i = 0; i < abilityLen; i++) {
            String abilityCurtName = abilityList.get(i).getV();
            boolean hasBool = false;
            int abilityDuty = i + 1;
            for (Entry<String, List<TeamImgEmpDto>> empEntry : m.entrySet()) {
                if (empEntry.getKey().equals(abilityCurtName)) {
                    KVItemDto kvItem = new KVItemDto();
                    kvItem.setK(String.valueOf((int) ArithUtil.mul(scale, abilityDuty)));
                    kvItem.setV(abilityCurtName + "级，" + empEntry.getValue().size() + "人");
                    dtos.add(kvItem);
                    hasBool = true;
                    continue;
                }
            }
            if (!hasBool) {
                KVItemDto kvItem = new KVItemDto();
                kvItem.setK(String.valueOf((int) ArithUtil.mul(scale, abilityDuty)));
                kvItem.setV(abilityCurtName + "级，0人");
                dtos.add(kvItem);
            }
        }
        return dtos;
    }

	private List<KVItemDto> packagAbility(List<KVItemDto> abilityCount, String customerId) {
		if(null == abilityCount){ return null;}
		List<KVItemDto> dtos = CollectionKit.newList();
		List<String> abilityKeyList = SysCache.queryAbilityKey.get();
		int abilityLen = abilityKeyList.size();
		double scale = ArithUtil.div(100.0, abilityLen, 2);

		for (int j = 0; j < abilityKeyList.size(); j++) {
			String dimKey = abilityKeyList.get(j);
			boolean hasBool = false;
			int abilityDuty = j + 1;
			for (KVItemDto dto : abilityCount) {
				String abilityKey = dto.getK();
				if (dimKey.equals(abilityKey)) {
					KVItemDto kvItem = new KVItemDto();
					kvItem.setK(String.valueOf((int) ArithUtil.mul(scale, abilityDuty)));
					kvItem.setV(dimKey + "级，" + dto.getV() + "人");
					dtos.add(kvItem);
					hasBool = true;
					continue;
				}
			}
			if (!hasBool) {
				KVItemDto kvItem = new KVItemDto();
				kvItem.setK(String.valueOf((int) ArithUtil.mul(scale, abilityDuty)));
				kvItem.setV(dimKey + "级，0人");
				dtos.add(kvItem);
			}
		}
		return dtos;
	}

    @Override
    public Map<Integer, Object> getPerformance(String organId, String customerId) {

    	List<PerformanceDto> rs = null;
    	List<PerformanceEmpDto> rsEmp=null;
    	EmployeePerformanceApi api=ApiManagerCenter.getApiDoc(EmployeePerformanceApi.class);
    	if(null!=api){
    		rs=api.queryPerformance(organId, customerId);
    		rsEmp=api.queryPerformanceEmp(organId, customerId);
    	}
//    	ApiVaild<List<PerformanceDto>> rsVaild=EmployeePerformanceApi.queryPerformance.get(organId, customerId);
//        if(rsVaild.isValid()){
//        	rs=rsVaild.getResult();
//        }
//        
//        ApiVaild<List<PerformanceEmpDto>> rsEmpVaild=EmployeePerformanceApi.queryPerformanceEmp.get(organId, customerId);
//        if(rsEmpVaild.isValid()){
//        	rsEmp=rsEmpVaild.getResult();
//        }
//        List<PerformanceDto> rs = manageHomeDao.queryPerformance(organId, customerId);
//        List<PerformanceEmpDto> rsEmp = manageHomeDao.queryPerformanceEmp(organId, customerId);

        Map<Integer, Object> rsMap = CollectionKit.newMap();
        rsMap.put(0, rs);
        rsMap.put(1, rsEmp);
        return rsMap;
    }

    /**
     * 员工近两周加班信息
     */
    public List<WorkOvertimeDto> getWorkOvertimeInfo(String empid, String customerId) {
        Integer cycle = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_REVIEWOTWEEK);    // 考察加班周期时间key
        //SY_OTTIME
        List<WorkOvertimeDto> rs = manageHomeDao.queryWorkOvertimeInfo(empid, customerId, DateUtil.getDBCurdate(), cycle);
        return rs;
    }


//    public List<WarnSynopsisDto> getWarnInfo(String organId, String customerId) {
//        List<WarnSynopsisDto> rs = manageHomeDao.queryWarnInfo(organId, customerId);
//        return rs;
//    }

    @Override
    public List<WarnInfoDto> queryLowPerformanceEmp(String organId, String customerId) {
        // 从缓存获取考察绩效次数
        Map<String, Object> map = CollectionKit.newMap();
        Double reviewPerfman = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.SY_REVIEWPERFMAN);    // 考察绩效次数key
        List<Integer> listRang = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.SY_LOWPERFMAN);
        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

        map.put("customerId", customerId);
        map.put("subOrganIdList", subOrganIdList);
        map.put("reviewPerfman", reviewPerfman);
        map.putAll(getFormanceRange(listRang));
        map.putAll(getDate(reviewPerfman));
        List<WarnInfoDto> rs = manageHomeDao.queryLowPerformanceEmp(map);
        return rs;
    }

    @Override
    public List<WarnInfoDto> queryHighPerformanceEmp(String organId, String customerId) {
        Map<String, Object> map = CollectionKit.newMap();
        Double reviewPerfman = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.SY_REVIEWPERFMAN);    // 考察绩效次数key
        List<Integer> listRang = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.SY_HEIGHTPERFMAN);
        //  String[] time = getDate(reviewPerfman);
        // 从缓存获取
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

        map.put("customerId", customerId);
        map.put("subOrganIdList", subOrganIdList);
        map.put("reviewPerfman", reviewPerfman);
        map.putAll(getFormanceRange(listRang));
        map.putAll(getDate(reviewPerfman));
        List<WarnInfoDto> rs = manageHomeDao.queryHighPerformanceEmp(map);

        return rs;
    }

    @Override
    public List<WarnInfoDto> queryRunOffWarnEmp(String customerId,
                                                String organId) {
        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

        List<WarnInfoDto> rs = manageHomeDao.queryRunOffWarnEmp(customerId, subOrganIdList);
        return rs;
    }

    @Override
    public Integer queryRunOffWarnCount(String customerId, String organId) {
        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

        Integer count = manageHomeDao.queryRunOffWarnCount(customerId, subOrganIdList);
        return count;
    }

    @Override
    public List<WarnInfoDto> queryOvertimeEmp(String customerId,
                                              String organId) {
//    	public static TimeBufferConfig<Integer> AvailabilityDayNum= 
//    			new TimeBufferConfigDefault<Integer>("queryAvailabilityDayNum",REFURBISH_YEAR_MONTH_DAY);
//    	
//    	
    	
    	// 从缓存获取有效工作日
        int availabilityDay = Cache.AvailabilityDayNum.get(PropertiesUtil.getProperty(ConfigKeyUtil.SY_REVIEWOTWEEK));
        // 从缓存获取考察加班周期
        Integer cycle = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.SY_REVIEWOTWEEK);
        // 从缓存获取加班时长预警
        double warnTime = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.SY_OTTIME);
        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);

        List<WarnInfoDto> rs = manageHomeDao.queryOvertimeEmp(customerId, subOrganIdList, availabilityDay, cycle, warnTime, DateUtil.getDBCurdate());
        return rs;
    }


    @Override
    public List<TalentDevelEmpDto> getTalentDevelExamItem(Integer yearInt, String empId, String customerId) {
        List<TalentDevelEmpDto> rs = manageHomeDao.queryTalentDevelExamItemData(yearInt, empId, customerId);
        return rs;
    }


    /**
     * 管理者首页-人才发展
     *
     * @param organId
     * @param customerId
     * @return
     */
    @Override
    public Map<Integer, Object> getTalentDevel(String organId, String customerId) {

        // 从缓存获取所有子孙机构ID
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(organId);
        // 测评最新时间
        int year = manageHomeDao.queryReportMaxYear(customerId);
        // 360测评data
        List<TalentDevelEmpDto> rs = manageHomeDao.queryTalentDevel(subOrganIdList, customerId, year);

        // 晋升数据
        List<TalentDevelEmpDto> promotionData = manageHomeDao.queryTalentDevelPromotionData(subOrganIdList, customerId);

        // 培训数据
        List<TalentDevelEmpDto> trainData = manageHomeDao.queryTalentDevelTrainData(subOrganIdList, customerId);

        // 测评数据
        List<TalentDevelEmpDto> examData = manageHomeDao.queryTalentDevelExamData(subOrganIdList, customerId, year);


        Map<Integer, Object> map = CollectionKit.newMap();
        Integer packTotal = packTotal(rs);
        List<TalentDevelEmpDto> packAvgValueDto = packAvgValue(rs);

        Integer quartPromoNum = 0;
        Double promoMonths = 0.0;
        Integer trainPerpol = 0;
        Double trainPerpolAvg = 0.0;
        if (null != promotionData) {
            quartPromoNum = packQuarterPromo(promotionData);
            promoMonths = packAvgPromoDate(promotionData);
        }
        if (null != trainData) {
            trainPerpol = trainData.size();
            trainPerpolAvg = packAvgTrainPerpol(trainData);
        }


        // 为了页面数组由0开始，map.key为0开始
        map.put(0, packTotal);
        map.put(1, packAvgValueDto);

        // 4个pie data
        map.put(2, quartPromoNum);
        map.put(3, promoMonths);
        map.put(4, trainPerpol);
        map.put(5, trainPerpolAvg);

        // 3个grid data
        map.put(6, promotionData);
        map.put(7, trainData);
        map.put(8, examData);

        /**
         * 测评最新时间
         */
        map.put(9, year);
        return map;
    }

    /**
     * 我的团队最近一次参加360测评总人数
     *
     * @param rs
     * @return
     */
    private Integer packTotal(List<TalentDevelEmpDto> rs) {

        if (null == rs) {
            return null;
        }
        List<String> rsList = CollectionKit.newList();
        for (TalentDevelEmpDto dto : rs) {
            String empId1 = dto.getEmpId();

            if (rsList.size() == 0) {
                rsList.add(empId1);
            }
            if (!(rsList.contains(empId1))) {
                rsList.add(empId1);
            }

        }
        return rsList.size();
    }

    /**
     * 计算同一测评项目里的：平均得分、参考人数
     *
     * @param rs
     * @return
     */
    private List<TalentDevelEmpDto> packAvgValue(List<TalentDevelEmpDto> rs) {
        MultiValueMap<String, TalentDevelEmpDto> m = new LinkedMultiValueMap<String, TalentDevelEmpDto>();
        for (TalentDevelEmpDto dto : rs) {
            if (dto.getAbilityName() != null) {
                m.add(dto.getAbilityName(), dto);
            }
        }
        List<TalentDevelEmpDto> avgScoreDtos = CollectionKit.newList();
        Double gainScore = 0.0;
        Integer abTotal = 0;
        for (Entry<String, List<TalentDevelEmpDto>> empEntry : m.entrySet()) {
            TalentDevelEmpDto avgScoreDto = new TalentDevelEmpDto();
            gainScore = 0.0;
            String mKey = empEntry.getKey();
            List<TalentDevelEmpDto> mValues = empEntry.getValue();
            abTotal = mValues.size();
            // 计算每个项目的总分
            for (TalentDevelEmpDto dto : mValues) {
                gainScore += dto.getGainScore();
            }
            avgScoreDto.setAbilityName(mKey);
            avgScoreDto.setGainScoreAvg(ArithUtil.div(gainScore, abTotal, 2));// 计算每个项目的平均分
            avgScoreDto.setAbTotal(abTotal);
            avgScoreDtos.add(avgScoreDto);
        }
        // 再为之前计算好的平均分的对象带入moduleType属性
        for (TalentDevelEmpDto dto : avgScoreDtos) {
            String abilityName = dto.getAbilityName();
            for (TalentDevelEmpDto soureDto : rs) {
                if (soureDto.getAbilityName().equals(abilityName)) {
                    dto.setModuleType(soureDto.getModuleType());
                    break;
                }
            }
        }
        return avgScoreDtos;
    }

    /**
     * 获取高绩效和低绩效的查询时间
     *
     * @return
     */
    private Map<String, String> getDate(Double num) {
        //	int num=4;
        num = num - 1;
        int record = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtil.getDate());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        String startDate = "";
        String endDate = "";
        int m = 0;  // 0代表 12  1代表06
        if (6 < month && month <= 12) {
            endDate = year + "" + "06";
            while (num > record) {
                if (record % 2 == 0) {
                    year--;
                    m = 0;
                } else {
                    m = 1;
                }
                record++;
            }
        } else {
            endDate = (year - 1) + "" + "12";
            while (num > record) {
                if (record % 2 != 0) {
                    year--;
                    m = 0;
                } else {
                    m = 1;
                }
                record++;
            }
        }
        startDate = year + "" + (m > 0 ? "06" : "12");

        Map<String, String> map = CollectionKit.newMap();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return map;
    }

    /**
     * 获取高绩效 低绩效范围
     *
     * @param listRang
     * @return
     */
    private Map<String, Integer> getFormanceRange(List<Integer> listRang) {
        Map<String, Integer> map = CollectionKit.newMap();
        Integer low = 0;
        Integer hight = 0;
        if (listRang != null) {
            if (listRang.size() == 1) {
//        		low=Integer.valueOf(""+listRang.get(0)) ;
//        		hight=low;
                low = listRang.get(0);
                hight = low;
            } else if (listRang.size() > 1) {
//        		low=Integer.valueOf(""+listRang.get(0)) ;
//        		hight=Integer.valueOf(""+listRang.get(1)) ;
                low = listRang.get(0);
                hight = listRang.get(listRang.size() - 1);
            }
        }
        map.put("low", low);
        map.put("hight", hight);
        return map;
    }

    /**
     * 当季晋升人数
     *
     * @param rs
     * @return
     */
    private Integer packQuarterPromo(List<TalentDevelEmpDto> rs) {
        Integer calRs = 0;
        calRs = rs.size();
        return calRs;
    }

    /**
     * 姓名		最后一次晋升时间~入职时间		晋升用时		晋升次数
     * 张三		2014.1.1-2010.2.2			48月			2
     * 李四 	2015.1.1-2010.1.1			60月			3
     * 王五		--
     * 21.6=(48+60)/(2+3)
     *
     * @param rs
     * @return
     */
    private Double packAvgPromoDate(List<TalentDevelEmpDto> rs) {
        Double calRs = 0.0;
        Double stayTime = 0.0;
        Integer pomoteNum = 0;
        for (TalentDevelEmpDto dto : rs) {
            stayTime += dto.getStayTime();
        }
        for (TalentDevelEmpDto dto : rs) {
            pomoteNum += dto.getPomoteNum();
        }
        if (0 < rs.size()) {
            calRs = ArithUtil.div(stayTime, pomoteNum, 2);
        }
        return calRs;
    }

    /**
     * 人均培训次数
     *
     * @param rs
     * @return
     */
    private Double packAvgTrainPerpol(List<TalentDevelEmpDto> rs) {
        Integer trainNum = 0;
        for (TalentDevelEmpDto dto : rs) {
            trainNum += dto.getTrainNum();
        }
        Double calRs = 0.0;
        if (0 < rs.size()) {
            calRs = ArithUtil.div(trainNum, rs.size(), 2);
        }
        return calRs;
    }


}

