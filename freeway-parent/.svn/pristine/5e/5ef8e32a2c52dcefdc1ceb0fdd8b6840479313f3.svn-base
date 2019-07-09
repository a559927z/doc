import {
    getParamInfoList,
    addParamInfo,
    deleteParamInfo,
    editParamInfo,
    getOneParamInfo,
  } from '../services/ParamsService';
  
  export default {
    namespace: 'paramsCong',
    state: {
      paramsObj: null, // 参数配置对象
    },
  
    effects: {
      *addParamInfo({ payload }, { call }) {
        const response = yield call(addParamInfo, payload);
        return response;
      },
      *editParamInfo({ payload }, { call }) {
        const response = yield call(editParamInfo, payload);
        return response;
      },
      *deleteParamInfo({ payload }, { call }) {
        const response = yield call(deleteParamInfo, payload);
        return response;
      },
      *getOneParamInfo({ payload }, { call }) {
        const response = yield call(getOneParamInfo, payload);
        return response;
      },
      *getParamInfoList({ payload }, { call, put }) {
        const response = yield call(getParamInfoList, payload);
        yield put({
          type: 'GET_PARAM_INFO_LIST',
          payload: response,
        });
      },
    },
  
    reducers: {
      GET_PARAM_INFO_LIST(state, action) {
        return {
          ...state,
          paramsObj: action.payload,
        };
      },
    },
  };
  