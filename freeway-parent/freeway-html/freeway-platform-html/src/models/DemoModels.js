import { message } from 'antd';
import { testSubmitForm, testQueryByPage, testDel, getRealTimeData } from '../services/DemoService';

export default {
  namespace: 'demo',
  state: {
    testAddFormSubmitting: false,
    loading: false,
    data: {
      data: {
        list: [],
      },
    },
    delLoading: false,
    delResult: {},
    realTimeResult: {},
  },
  // https://www.cnblogs.com/heyuqing/p/6854851.html
  // 当某个action触发后，saga可以使用call、fetch等api发起异步操作，
  // 操作完成后使用put函数触发action，同步更新state，从而完成整个State的更新。
  //  put是saga对Redux中dispatch方法的一个封装，调用put方法后，saga内部会分发action通知Store更新state。
  effects: {
    *submitTestAddForm({ payload }, { call, put }) {
      // console.log("effects *submitAddTestForm:" , payload);
      // yield关键字来以同步的方式实现异步操作
      // 触发某个action， 作用和dispatch相同：

      // put函数 ，这个函数用于创建 dispatch Effect
      yield put({
        type: 'changeTestAddFormSubmitting',
        payload: true, // 开始loading
      });
      // 调用api testSubmitForm
      const response = yield call(testSubmitForm, payload);
      yield put({
        type: 'changeTestAddFormSubmitting',
        payload: false, // 获取数据后结束loading
      });
      message.success('提交成功');
      console.log('------------', response);
    },
    *fetch({ payload }, { call, put }) {
      // console.log("effects *fetch:" , payload);
      yield put({
        type: 'fetchLoading',
        payload: true,
      });
      const response = yield call(testQueryByPage, payload);
      // console.log("effects *fetch:" , response);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'fetchLoading',
        payload: false,
      });
    },
    *del({ payload }, { call, put }) {
      console.log('action发起delLoading动作,通知Reducer更改delLoading的状态为：true');
      yield put({
        type: 'delLoading',
        payload: true,
      });
      console.log('action发起http请求:', testDel, payload);
      const response = yield call(testDel, payload);
      console.log(
        'action接收数据完成，发起delResult动作，通知Reducer将数据保存到state的data中:',
        response
      );
      yield put({
        type: 'delResult',
        payload: response,
      });
      console.log(
        'action将数据保存进state中完成，发起delLoading动作， 通知reducer将delLoading状态改为false'
      );
      yield put({
        type: 'delLoading',
        payload: false,
      });
    },
    *getRealTimeData({ payload }, { call, put }) {
      yield put({
        type: 'getRealTimeDataLoading',
        payload: true,
      });
      const response = yield call(getRealTimeData, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'getRealTimeDataLoading',
        payload: false,
      });
    },
  },

  reducers: {
    changeTestAddFormSubmitting(state, { payload }) {
      // console.log("reducers changeTestAddFormSubmitting:" , payload);
      return {
        ...state,
        testAddFormSubmitting: payload,
      };
    },
    save(state, action) {
      // console.log("reducers接收action获取的数据，并保存到state中" , action);
      return {
        ...state,
        data: action.payload,
      };
    },
    fetchLoading(state, action) {
      // console.log("reducers接收action请求，更改changeLoading状态：" , action.payload);
      return {
        ...state,
        loading: action.payload,
      };
    },
    delResult(state, action) {
      console.log('reducers接收action获取的数据，并保存到state中', action);
      return {
        ...state,
        delResult: action.payload,
      };
    },
    delLoading(state, action) {
      console.log('reducers接收action请求，更改delLoading状态：', action.payload);
      return {
        ...state,
        delLoading: action.payload,
      };
    },
    getRealTimeDataLoading(state, action) {
      console.log('reducers接收action请求，更改delLoading状态：', action.payload);
      return {
        ...state,
        realTimeResult: action.payload,
      };
    },
  },
};
