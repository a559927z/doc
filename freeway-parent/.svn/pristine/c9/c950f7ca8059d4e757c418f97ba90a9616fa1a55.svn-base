import { message } from 'antd';
import {
  selectByPage,
  save,
  saveRecordList,
  del,
  transform,
  viewDetial,
  selectAllRecord,
} from '../services/ContactRecordService';

export default {
  namespace: 'contactRecord', // 这个名称是在页面的时候使用的
  state: {
    data: {
      // 列表返回的结果
      list: [],
      pageNum: 1,
      total: 0,
    },
    record: {}, // 单条记录返回的结果
    calendarRecord: [],
  },
  // 以下方法是在页面中调用的,前面必须加星号,call里面的是service里面的方法
  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield call(selectByPage, payload);
      yield put({
        type: 'queryListReducer',
        payload: response,
      });
    },
    *selectAllRecord({ payload }, { call, put }) {
      const response = yield call(selectAllRecord, payload);
      yield put({
        type: 'saveCalendarRecord',
        payload: response,
      });
    },
    *submit({ payload }, { call }) {
      yield call(save, payload);
      message.success('保存成功');
    },
    *saveRecordList({ payload }, { call }) {
      const response = yield call(saveRecordList, payload);
      return response;
    },
    *del({ payload }, { call }) {
      const response = yield call(del, payload);
      if (response.data === 0) {
        message.warn('删除失败');
      }
      message.success('删除成功');
      return response;
    },
    *transform({ payload }, { call }) {
      const response = yield call(transform, payload);
      if (response.data === 0) {
        message.warn('转换失败');
      }
      message.success('转换成功');
      return response;
    },
    *viewDetial({ payload }, { call, put }) {
      const response = yield call(viewDetial, payload);
      yield put({
        type: 'viewDetialReducer',
        payload: response,
      });
    },
  },

  // 下面方法是给上面effects里面调用的，调用方式为 type=xxxx
  reducers: {
    queryListReducer(state, action) {
      return {
        ...state,
        data: action.payload.data,
      };
    },
    viewDetialReducer(state, action) {
      return {
        ...state,
        record: action.payload.data,
      };
    },
    saveCalendarRecord(state, action) {
      return {
        ...state,
        calendarRecord: action.payload.data,
      };
    },
  },
};
