import { stringify } from 'qs';
import request from '../utils/request';

// 通过标签类型查询标签列表
// export async function getTagByType(params) {
// 	return request(`/data/contact/tag/getTagByType?${stringify(params)}`);
// }

// 分页查询列表数据
export async function queryTenantList(params) {
  return request('/pub/tenantInfo/selectByPage', {
    method: 'POST',
    body: params
  });
}


// 新增
export async function saveTenantData(params) {
  return request('/pub/tenantInfo/save', {
    method: 'POST',
    body: params
  });
}

// 修改
export async function modifyTenantData(params) {
  return request('/pub/tenantInfo/updateById', {
    method: 'POST',
    body: params
  });
}

// 删除
export async function deleteTenantData(params) {
  return request('/pub/tenantInfo/deleteById', {
    method: 'POST',
    body: params
  });
}