import { stringify } from 'qs';
import request from '../utils/request';


export async function testSubmitForm(params) {
  return request('/data/test/testSave', {
    method: 'POST',
    body: params,
  });
}

export async function testQueryByPage(params) {
    return request('/data/sys/log/selectByPage', {
        method: 'POST',
        body: params,
    });
}