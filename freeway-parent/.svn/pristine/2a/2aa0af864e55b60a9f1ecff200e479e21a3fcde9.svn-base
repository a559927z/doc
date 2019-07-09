import { stringify } from 'qs';
import request from '../utils/request';

export async function getAllLocationData(params) {
  return request('/data/contact/location/getAllLocationData', {
    method: 'POST',
    body: params,
  });
}

export async function queryContactListByPage(params) {
  return request('/data/contact/contact/selectByPage', {
    method: 'POST',
    body: params,
});
}

export async function getContactDetailById(params) {
  return request('/data/contact/contact/getContactDetailById', {
    method: 'POST',
    body: params,
});
}

export async function getSelectItems(params) {
  return request('/data/contact/tag/getSelectItems', {
    method: 'POST',
    body: params,
});
}

export async function getSearchSelectItems(params) {
  return request('/data/contact/tag/getSearchSelectItems', {
    method: 'POST',
    body: params,
  });
}

export async function getLibraryLeafTag(params) {
  return request('/data/contact/tag/getLibraryLeafTag', {
    method: 'POST',
    body: params,
});
}

export async function checkContactName(params) {
  return request(`/data/contact/contact/checkContactName?${stringify(params)}`);
}

export async function submitContactAccountForm(params) {
  return request('/data/contact/contact/addBaseContact', {
    method: 'POST',
    body: params,
  });
}

export async function submitContactForm(params) {
  return request('/data/contact/contact/addOrUpdateContact', {
      method: 'POST',
      body: params,
  });
}

export async function submitEducation(params) {
  return request('/data/contact/contactEducational/addContactEducation', {
      method: 'POST',
      body: params,
  });
}

export async function submitResume(params) {
  return request('/data/contact/contractResume/addContactEducation', {
      method: 'POST',
      body: params,
  });
}

export async function submitContactTag(params) {
  return request('/data/contact/contactTag/addContactTag', {
      method: 'POST',
      body: params,
  });
}

export async function delContacts(params) {
  return request('/data/contact/contact/delContacts', {
      method: 'POST',
      body: params,
  });
}

export async function addEnumTag(params) {
  return request('/data/contact/tag/addEnumTag', {
      method: 'POST',
      body: params,
  });
}

export async function addLibraryTag(params) {
  return request('/data/contact/tag/addLibraryTag', {
      method: 'POST',
      body: params,
  });
}

export async function upload(params) {
  return request('/data/file/singleFileUpload', {
      method: 'POST',
      body: params,
  });
}

export async function selectList(){
    return request('/data/contact/contact/selectList');
}

export async function calculateContact(){
    return request('/data/contact/contact/calculateContact');
}

export async function getPhotoList(){
    return request('/data/contact/contact/getPhotoList');
}
