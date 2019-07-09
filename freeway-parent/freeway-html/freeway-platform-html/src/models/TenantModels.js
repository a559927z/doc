import { message } from 'antd';
import {
  queryTenantList,
  saveTenantData,
  modifyTenantData,
  deleteTenantData,
} from '../services/TenantService';

export default {
  namespace: 'tenantModels',
  state: {
    connectionObj: null,
  },

  effects: {
    *getDataMap({ payload }, { call, put }) {
      const response = yield call(queryTenantList, payload);
      yield put({
        type: 'GET_LIST_DATA_MAP',
        payload: response,
      });
    },
    *modifyTenantData({ payload }, { call }) {
      const response = yield call(modifyTenantData, payload);
      return response;
    },
    *saveTenantData({ payload }, { call }) {
      const response = yield call(saveTenantData, payload);
      return response;
    },
    *deleteTenantData({ payload }, { call }) {
      const response = yield call(deleteTenantData, payload);
      console.log(response);
      return response;
    },
  },

  reducers: {
    'GET_LIST_DATA_MAP'(state, action) {
      console.log(action);
      return {
        ...state,
        connectionObj: action.payload ? action.payload.data : {},
      }
    }
  }
}