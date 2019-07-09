import { queryUserList, queryCurrent } from '@/services/user';

export default {
  namespace: 'user',

  state: {
    list: [],
    currentUser: {},
  },
  
  effects: {
    *queryUserList(_, { call, put }) {
      const response = yield call(queryUserList);
      yield put({
        type: 'save',
        payload: response,
      });
    },
    *fetchCurrent(_, { call, put }) {
      const response = yield call(queryCurrent);
      yield put({
        type: 'saveCurrentUser',
        payload: response,
      });
    },
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        list: action.payload.data,
      };
    },
    saveCurrentUser(state, action) {
      // console.log('保存当前用户到localStorage:',action.payload.data);
      localStorage.setItem('currentUser', JSON.stringify(action.payload.data));
      return {
        ...state,
        currentUser: action.payload.data || {},
      };
    },
    changeNotifyCount(state, action) {
      return {
        ...state,
        currentUser: {
          ...state.currentUser,
          notifyCount: action.payload.totalCount,
          unreadCount: action.payload.unreadCount,
        },
      };
    },
  },
};
