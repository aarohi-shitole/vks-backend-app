import dayjs from 'dayjs/esm';

import { AssetType } from 'app/entities/enumerations/asset-type.model';

import { IMemberAssets, NewMemberAssets } from './member-assets.model';

export const sampleWithRequiredData: IMemberAssets = {
  id: 35816,
};

export const sampleWithPartialData: IMemberAssets = {
  id: 69837,
  assetAmount: 43635,
  otherDocument2: '../fake-data/blob/hipster.png',
  otherDocument2ContentType: 'unknown',
  assetArea: 91101,
  lastModifiedBy: 'Steel Dynamic Granite',
  createdBy: 'Berkshire Program generating',
};

export const sampleWithFullData: IMemberAssets = {
  id: 95549,
  assetAmount: 62971,
  otherDocument1: '../fake-data/blob/hipster.png',
  otherDocument1ContentType: 'unknown',
  otherDocument2: '../fake-data/blob/hipster.png',
  otherDocument2ContentType: 'unknown',
  assetType: AssetType['FARM_MACHINERY'],
  assetArea: 66811,
  assetAddress: 'Market Nebraska',
  numberOfAssets: 54779,
  lastModified: dayjs('2022-08-05T02:34'),
  lastModifiedBy: 'Dakota gold',
  createdBy: 'Cambridgeshire convergence withdrawal',
  createdOn: dayjs('2022-08-05T00:16'),
  isDeleted: false,
};

export const sampleWithNewData: NewMemberAssets = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
