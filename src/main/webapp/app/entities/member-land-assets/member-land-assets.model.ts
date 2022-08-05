import dayjs from 'dayjs/esm';

export interface IMemberLandAssets {
  id: number;
  landType?: string | null;
  landGatNo?: string | null;
  landAreaInHector?: number | null;
  jindagiPatrakNo?: string | null;
  jindagiAmount?: number | null;
  assetLandAddress?: string | null;
  valueOfLand?: number | null;
  assigneeOfLand?: boolean | null;
  isDeleted?: boolean | null;
  mLLoanNo?: number | null;
  jindagiPatrak?: string | null;
  jindagiPatrakContentType?: string | null;
  eightA?: string | null;
  eightAContentType?: string | null;
  saatBara?: string | null;
  saatBaraContentType?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
}

export type NewMemberLandAssets = Omit<IMemberLandAssets, 'id'> & { id: null };
