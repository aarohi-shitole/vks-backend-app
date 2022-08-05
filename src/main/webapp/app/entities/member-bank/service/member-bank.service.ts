import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMemberBank, NewMemberBank } from '../member-bank.model';

export type PartialUpdateMemberBank = Partial<IMemberBank> & Pick<IMemberBank, 'id'>;

type RestOf<T extends IMemberBank | NewMemberBank> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestMemberBank = RestOf<IMemberBank>;

export type NewRestMemberBank = RestOf<NewMemberBank>;

export type PartialUpdateRestMemberBank = RestOf<PartialUpdateMemberBank>;

export type EntityResponseType = HttpResponse<IMemberBank>;
export type EntityArrayResponseType = HttpResponse<IMemberBank[]>;

@Injectable({ providedIn: 'root' })
export class MemberBankService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/member-banks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(memberBank: NewMemberBank): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberBank);
    return this.http
      .post<RestMemberBank>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(memberBank: IMemberBank): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberBank);
    return this.http
      .put<RestMemberBank>(`${this.resourceUrl}/${this.getMemberBankIdentifier(memberBank)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(memberBank: PartialUpdateMemberBank): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberBank);
    return this.http
      .patch<RestMemberBank>(`${this.resourceUrl}/${this.getMemberBankIdentifier(memberBank)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMemberBank>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMemberBank[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMemberBankIdentifier(memberBank: Pick<IMemberBank, 'id'>): number {
    return memberBank.id;
  }

  compareMemberBank(o1: Pick<IMemberBank, 'id'> | null, o2: Pick<IMemberBank, 'id'> | null): boolean {
    return o1 && o2 ? this.getMemberBankIdentifier(o1) === this.getMemberBankIdentifier(o2) : o1 === o2;
  }

  addMemberBankToCollectionIfMissing<Type extends Pick<IMemberBank, 'id'>>(
    memberBankCollection: Type[],
    ...memberBanksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const memberBanks: Type[] = memberBanksToCheck.filter(isPresent);
    if (memberBanks.length > 0) {
      const memberBankCollectionIdentifiers = memberBankCollection.map(memberBankItem => this.getMemberBankIdentifier(memberBankItem)!);
      const memberBanksToAdd = memberBanks.filter(memberBankItem => {
        const memberBankIdentifier = this.getMemberBankIdentifier(memberBankItem);
        if (memberBankCollectionIdentifiers.includes(memberBankIdentifier)) {
          return false;
        }
        memberBankCollectionIdentifiers.push(memberBankIdentifier);
        return true;
      });
      return [...memberBanksToAdd, ...memberBankCollection];
    }
    return memberBankCollection;
  }

  protected convertDateFromClient<T extends IMemberBank | NewMemberBank | PartialUpdateMemberBank>(memberBank: T): RestOf<T> {
    return {
      ...memberBank,
      lastModified: memberBank.lastModified?.toJSON() ?? null,
      createdOn: memberBank.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMemberBank: RestMemberBank): IMemberBank {
    return {
      ...restMemberBank,
      lastModified: restMemberBank.lastModified ? dayjs(restMemberBank.lastModified) : undefined,
      createdOn: restMemberBank.createdOn ? dayjs(restMemberBank.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMemberBank>): HttpResponse<IMemberBank> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMemberBank[]>): HttpResponse<IMemberBank[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
