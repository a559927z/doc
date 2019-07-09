import {
  queryContactListByPage,
  getSelectItems,
  getLibraryLeafTag,
  delContacts,
  checkContactName,
  submitContactAccountForm,
  submitContactForm,
  submitEducation,
  submitResume,
  submitContactTag,
  addEnumTag,
  addLibraryTag,
  getContactDetailById,
  selectList,
  getAllLocationData,
  getSearchSelectItems,
} from '../services/ContactService';

export default {
  namespace: 'contactMgr',
  // initial_state
  state: {
    contactObj: null, // 联系人
    contactList: [], // 不分页的全部联系人
    locationList: [], // 地区信息
    selectItems: [], // 表单下拉选项值
    tagItemList: [], // 联系人标签
    contactDetail: null,
    searchSelectItems: {}, // 列表查询下拉
  },

  // 订阅一个数据源，然后根据条件 dispatch 需要的 action
  // 数据源可以是当前的时间、 键盘输入、 路由变化等等
  // subscriptions: {
  // 监听地址
  // history.listen(location => {
  //   if (location.pathname.includes('')) {
  //     dispatch({
  //     })
  //   }
  // })
  // },

  // sagas监听发起的action， 然后决定基于这个action来做什么
  // generator函数实现
  // effects：要被 saga middleware 执行的信息
  // call(fn(),[])： 阻塞执行， call() 执行完， 才会往下执行
  // put(action)： 发起一个 action 到 store
  // select(fn(state))：得到 Store 中的 state 中的数据
  effects: {
    *getContactList({ payload }, { call, put }) {
      const response = yield call(queryContactListByPage, payload);
      yield put({
        type: 'saveContactList',
        payload: response,
      });
    },
    *getSearchSelectItems({ payload }, { call, put }) {
      const response = yield call(getSearchSelectItems, payload);
      yield put({
        type: 'saveSearchSelectItems',
        payload: response,
      });
      return response;
    },
    *delContact({ payload }, { call }) {
      const response = yield call(delContacts, payload);
      return response;
    },
    *getContactDetail({ payload }, { call, put }) {
      const response = yield call(getContactDetailById, payload);
      yield put({
        type: 'getDetail',
        payload: response,
      });
      return response;
    },
    *getSelectItems({ payload }, { call, put }) {
      const response = yield call(getSelectItems, payload);
      yield put({
        type: 'saveSelectItems',
        payload: response,
      });
      return response;
    },
    *getLibraryLeafTag({ payload }, { call, put }) {
      const response = yield call(getLibraryLeafTag, payload);
      yield put({
        type: 'saveTagItemList',
        payload: response,
      });
      return response;
    },
    *saveAccountInfo({ payload }, { call }) {
      const response = yield call(submitContactAccountForm, payload);
      return response;
    },
    *saveBaseInfo({ payload }, { call }) {
      const response = yield call(submitContactForm, payload);
      return response;
    },
    *saveEducation({ payload }, { call }) {
      const response = yield call(submitEducation, payload);
      return response;
    },
    *saveResume({ payload }, { call }) {
      const response = yield call(submitResume, payload);
      return response;
    },
    *saveTags({ payload }, { call }) {
      const response = yield call(submitContactTag, payload);
      return response;
    },
    *saveEnumTag({ payload }, { call }) {
      const response = yield call(addEnumTag, payload);
      return response;
    },
    *addLibraryTag({ payload }, { call }) {
      const response = yield call(addLibraryTag, payload);
      return response;
    },
    *selectList(_, { call, put }) {
      const response = yield call(selectList);
      yield put({
        type: 'selectListReducer',
        payload: response,
      });
      return response;
    },
    *getAllLocationData(_, { call, put }) {
      const response = yield call(getAllLocationData);
      yield put({
        type: 'savelocationList',
        payload: response,
      });
      return response;
    },
    *checkContactName({ payload }, { call }) {
      const response = yield call(checkContactName, payload);
      return response;
    },
  },

  reducers: {
    getDetail(state, action) {
      return {
        ...state,
        contactDetail: action.payload.data,
      };
    },
    saveContactList(state, action) {
      return {
        ...state,
        contactObj: action.payload.data,
      };
    },
    selectListReducer(state, action) {
      return {
        ...state,
        contactList: action.payload.data,
      };
    },
    saveSelectItems(state, action) {
      return {
        ...state,
        selectItems: action.payload.data,
      };
    },
    saveSearchSelectItems(state, action) {
      return {
        ...state,
        searchSelectItems: action.payload.data,
      };
    },
    saveTagItemList(state, action) {
      return {
        ...state,
        tagItemList: action.payload.data,
      };
    },
    savelocationList(state, action) {
      return {
        ...state,
        locationList: action.payload.data,
      };
    },
  },
};
