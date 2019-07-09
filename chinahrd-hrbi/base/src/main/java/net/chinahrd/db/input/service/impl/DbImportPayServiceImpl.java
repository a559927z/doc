package net.chinahrd.db.input.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.db.input.service.DbImportPayService;

/**
 * 薪酬看板
 * 
 * @author jxzhang on 2016-04-01
 *
 */
@Transactional
@Service("dbImportPayServiceImpl")
public class DbImportPayServiceImpl implements DbImportPayService {

	@Override
	public boolean test() {
		// TODO Auto-generated method stub
		return false;
	}}
