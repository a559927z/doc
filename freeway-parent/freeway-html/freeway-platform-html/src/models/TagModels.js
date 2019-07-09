import { message } from 'antd';
import {
  getTagByType,
  addLibraryTag,
  getAllLibraryTag,
  addEnumTag,
  delTag,
  updateTag,
} from '../services/TagService';

import { listToTree, findNodeByRecursion } from '@/utils/utils';

export default {
  namespace: 'tag',
  state: {
    tagList: [],
    libTagTree: [],
    libTagList: [],
  },
  effects: {
    *getTagListByType({ payload }, { call, put }) {
      const response = yield call(getTagByType, payload);
      yield put({
        type: 'queryListReducer',
        payload: response,
      });
      return response;
    },
    *getAllTag({ payload }, { call, put }) {
      const response = yield call(getAllLibraryTag, payload);
      yield put({
        type: 'queryLibTagReducer',
        payload: response,
      });
    },
    *addLibraryTag({ payload }, { call, select, put }) {
      const response = yield call(addLibraryTag, payload);
      if (response.code === 1) {
        const libTagTree = yield select(state => state.tag.libTagTree);
        if (payload.parentId) {
          findNodeByRecursion(
            libTagTree,
            {
              idKey: 'tagId',
              childrenKey: 'children',
            },
            payload.parentId,
            false,
            node => {
              if (!node.children) {
                // eslint-disable-next-line no-param-reassign
                node.children = [];
              }
              node.children.push(response.data);
            }
          );
        } else {
          libTagTree.push(response.data);
        }
        yield put({
          type: 'delLibTagReducer',
          payload: {},
        });
        message.success('标签添加成功！');
        return response;
      }
      message.error(response.message);
      return response;
    },
    *updateTag({ payload }, { call, select, put }) {
      const response = yield call(updateTag, payload);
      if (response.code === 1) {
        const libTagTree = yield select(state => state.tag.libTagTree);
        findNodeByRecursion(
          libTagTree,
          {
            idKey: 'tagId',
            childrenKey: 'children',
          },
          payload.tagId,
          false,
          node => {
            Object.assign(node, response.data);
          }
        );
        yield put({
          type: 'delLibTagReducer',
          payload: {},
        });
        message.success('更新成功！');
        return response;
      }
      message.error(response.message);
      return response;
    },
    *addEnumTag({ payload }, { call }) {
      yield call(addEnumTag, payload);
      message.success('添加成功！');
    },
    *delTag({ payload }, { call, put, select }) {
      const response = yield call(delTag, payload);
      if (response.code === 1) {
        const libTagTree = yield select(state => state.tag.libTagTree);
        findNodeByRecursion(
          libTagTree,
          {
            idKey: 'tagId',
            childrenKey: 'children',
          },
          payload.id,
          true
        );
        yield put({
          type: 'delLibTagReducer',
          payload: {},
        });
        message.success('删除成功！');
        return;
      }
      message.error(response.message);
    },
  },
  reducers: {
    queryListReducer(state, action) {
      return {
        ...state,
        tagList: action.payload.data,
      };
    },
    queryLibTagReducer(state, action) {
      const { data } = action.payload;
      const libTagTree = listToTree(data, {
        idKey: 'tagId',
        parentKey: 'parentId',
      });
      return {
        ...state,
        libTagTree,
        libTagList: data,
      };
    },
    delLibTagReducer(state) {
      return {
        ...state,
      };
    },
  },
};
