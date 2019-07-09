import { stringify } from 'qs';
import request from '../utils/request';
// import { func } from 'prop-types';

// 通过标签类型查询标签列表
// export async function getTagByType(params) {
// 	return request(`/data/contact/tag/getTagByType?${stringify(params)}`);
// }

// 分页查询列表数据
export async function queryDataBaseList(params) {
  return request('/pub/datasource/selectByPage', {
    method: 'POST',
    body: params
  });
}

// 新增
export async function saveDataBase(params) {
  return request('/pub/datasource/save', {
    method: 'POST',
    body: params
  });
}

// 修改
export async function modifyDataBase(params) {
  return request('/pub/datasource/updateById', {
    method: 'POST',
    body: params
  });
}

// 删除
export async function deleteDataBase(params) {
  return request('/pub/datasource/deleteById', {
    method: 'POST',
    body: params
  });
}