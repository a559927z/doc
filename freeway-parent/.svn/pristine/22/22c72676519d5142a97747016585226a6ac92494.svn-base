import {
  addIniiativeMaps,
  getAllIniiativeMap,
  delIniiativeMap,
  modifyIniiativeMap,
} from '../services/ConnectionService';

export default {
  namespace: 'connectionMgr',
  state: {
    connectionObj: null, // 人脉信息
  },

  effects: {
    *saveContactMap({ payload }, { call }) {
      const response = yield call(addIniiativeMaps, payload);
      return response;
    },
    *getAllIniiativeMap({ payload }, { call, put }) {
      const response = yield call(getAllIniiativeMap, payload);
      yield put({
        type: 'GET_ALL_INIIATIVE_MAP',
        payload: response,
      });
    },
    *delIniiativeMap({ payload }, { call }) {
      const response = yield call(delIniiativeMap, payload);
      return response;
    },
    *modifyIniiativeMap({ payload }, { call }) {
      const response = yield call(modifyIniiativeMap, payload);
      return response;
    },
  },

  reducers: {
    GET_ALL_INIIATIVE_MAP(state, action) {
      return {
        ...state,
        connectionObj: action.payload.data,
      };
    },
  },
};
