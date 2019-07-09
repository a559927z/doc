import { message } from 'antd';
import { calculateContact, getPhotoList } from '../services/ContactService';

import {
  calculateRecord,
  calculateDatePlan,
  selectDatePlanPage,
  noRemid,
} from '../services/ContactRecordService';

export default {
  namespace: 'homeMgr',
  state: {
    contactObj: {}, // 联系人
    photoList: [], // 头像
    associateObj: {}, // 交往
    dateObj: {}, // 预约
    datePlanList: [], // 预约记录
  },

  effects: {
    *calculateContact({ payload }, { call, put }) {
      const response = yield call(calculateContact, payload);
      yield put({
        type: 'CALCULATE_CONTACT',
        payload: response,
      });
      return response;
    },
    *getPhotoList({ payload }, { call, put }) {
      const response = yield call(getPhotoList, payload);
      yield put({
        type: 'GET_PHOTO_LIST',
        payload: response,
      });
      return response;
    },

    *calculateRecord({ payload }, { call, put }) {
      const response = yield call(calculateRecord, payload);
      yield put({
        type: 'CALCULATE_RECORD',
        payload: response,
      });
      return response;
    },
    *calculateDatePlan({ payload }, { call, put }) {
      const response = yield call(calculateDatePlan, payload);
      yield put({
        type: 'CALCULATE_DATE_PLAN',
        payload: response,
      });
      return response;
    },
    *selectDatePlanPage({ payload }, { call, put }) {
      const response = yield call(selectDatePlanPage, payload);
      yield put({
        type: 'SELECT_DATE_PLAN_PAGE',
        payload: response,
      });
      return response;
    },
    *noRemid({ payload }, { call, put, select }) {
      const response = yield call(noRemid, payload);
      if (response.code === 1) {
        const list = yield select(state => state.homeMgr.datePlanList);
        const index = list.findIndex(item => item.recordId === payload.recordId);
        if (index > -1) {
          list.splice(index, 1);
        }
        yield put({
          type: 'deafult',
          payload: {},
        });
        message.success('设置成功！');
        return;
      }
      message.error(response.message);
    },
  },

  reducers: {
    deafult(state) {
      return {
        ...state,
      };
    },
    CALCULATE_CONTACT(state, action) {
      return {
        ...state,
        contactObj: action.payload.data,
      };
    },
    GET_PHOTO_LIST(state, action) {
      return {
        ...state,
        photoList: action.payload.data,
      };
    },
    CALCULATE_RECORD(state, action) {
      return {
        ...state,
        associateObj: action.payload.data,
      };
    },
    CALCULATE_DATE_PLAN(state, action) {
      return {
        ...state,
        dateObj: action.payload.data,
      };
    },
    SELECT_DATE_PLAN_PAGE(state, action) {
      return {
        ...state,
        datePlanList: action.payload.data,
      };
    },
  },
};
