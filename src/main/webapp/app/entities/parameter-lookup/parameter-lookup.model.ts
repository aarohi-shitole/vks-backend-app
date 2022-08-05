import dayjs from 'dayjs/esm';
import { ParameterType } from 'app/entities/enumerations/parameter-type.model';

export interface IParameterLookup {
  id: number;
  parameterType?: ParameterType | null;
  description?: string | null;
  displayValue?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
}

export type NewParameterLookup = Omit<IParameterLookup, 'id'> & { id: null };
