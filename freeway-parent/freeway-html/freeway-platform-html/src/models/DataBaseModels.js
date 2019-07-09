import { message } from 'antd';
import {
  queryDataBaseList,
  modifyDataBase,
  saveDataBase,
  deleteDataBase
} from '../services/DataBaseService';

export default {
  namespace: 'databaseModels',
  state: {
    connectionObj: null,
  },

  effects: {
    *getListData({ payload }, { call, put }) {
      const response = yield call(queryDataBaseList, payload);
      yield put({
        type: 'GET_LIST_DATA_MAP',
        payload: response,
      });
    },
    *modifyDataBase({ payload }, { call }) {
      const response = yield call(modifyDataBase, payload);
      return response;
    },
    *saveDataBase({ payload }, { call }) {
      const response = yield call(saveDataBase, payload);
      return response;
    },
    *deleteDataBase({ payload }, { call }) {
      const response = yield call(deleteDataBase, payload);
      console.log(response);
      return response;
    },
  },

  reducers: {
    'GET_LIST_DATA_MAP'(state, action) {
      return {
        ...state,
        connectionObj: action.payload ? action.payload.data : {},
      }
    }
  }
}