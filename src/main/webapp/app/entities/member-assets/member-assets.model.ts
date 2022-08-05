import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';
import { AssetType } from 'app/entities/enumerations/asset-type.model';

export interface IMemberAssets {
  id: number;
  assetAmount?: number | null;
  otherDocument1?: string | null;
  otherDocument1ContentType?: string | null;
  otherDocument2?: string | null;
  otherDocument2ContentType?: string | null;
  assetType?: AssetType | null;
  assetArea?: number | null;
  assetAddress?: string | null;
  numberOfAssets?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewMemberAssets = Omit<IMemberAssets, 'id'> & { id: null };
