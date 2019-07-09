import { routerRedux } from 'dva/router';
import { stringify } from 'qs';
import { login, logout } from '@/services/api';
import { setAuthority } from '@/utils/authority';
import { getPageQuery } from '@/utils/utils';
import { reloadAuthorized } from '@/utils/Authorized';
import { message } from 'antd';

import { loginCheck, modifyPasswd } from '../services/api';

export default {
  namespace: 'login',

  state: {
    status: undefined,
    userStatus: undefined,
  },

  effects: {
    *login({ payload }, { call, put }) {
      const response = yield call(login, payload);
      console.log('response:', response);
      yield put({
        type: 'changeLoginStatus',
        payload: response,
      });
      // Login successfully
      if (response.code === 1 || response.status === 'ok') {
        reloadAuthorized();
        const urlParams = new URL(window.location.href);
        const params = getPageQuery();
        let { redirect } = params;
        if (redirect) {
          const redirectUrlParams = new URL(redirect);
          if (redirectUrlParams.origin === urlParams.origin) {
            redirect = redirect.substr(urlParams.origin.length);
            if (redirect.match(/^\/.*#/)) {
              redirect = redirect.substr(redirect.indexOf('#') + 1);
            }
          } else {
            window.location.href = redirect;
            return;
          }
        }
        yield put(routerRedux.replace(redirect || '/'));
        return;
      }
      message.error(response.message);
    },

    *logout(_, { call, put }) {
      yield call(logout);
      localStorage.removeItem('currentUser');
      yield put({
        type: 'changeLoginStatus',
        payload: {
          status: false,
          currentAuthority: 'guest',
        },
      });
      reloadAuthorized();
      yield put(
        routerRedux.push({
          pathname: '/user/login',
          search: stringify({
            redirect: window.location.href,
          }),
        })
      );
    },
    *loginCheck({ payload }, { call, put }) {
      const response = yield call(loginCheck, payload);
      if (response.code === 1) {
        yield put({
          type: 'loginCheckReducer',
          payload: response,
        });
        return;
      }
      message.error(response.message);
    },
    *modifyPasswd({ payload }, { call }) {
      const response = yield call(modifyPasswd, payload);
      if (response.code === 1) {
        message.success('修改成功！');
        return response;
      }
      message.error(response.message);
      return null;
    },
  },

  reducers: {
    changeLoginStatus(state, { payload }) {
      console.log('设置路由权限', payload);
      if (payload.code === 1 || payload.status === 'ok') {
        setAuthority('admin');
      }
      return {
        ...state,
        status: payload.status,
        type: payload.type,
      };
    },
    loginCheckReducer(state, action) {
      return {
        ...state,
        userStatus: action.payload.data,
      };
    },
  },
};
