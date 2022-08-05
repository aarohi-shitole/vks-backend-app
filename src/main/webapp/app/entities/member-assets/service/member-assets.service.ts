import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMemberAssets, NewMemberAssets } from '../member-assets.model';

export type PartialUpdateMemberAssets = Partial<IMemberAssets> & Pick<IMemberAssets, 'id'>;

type RestOf<T extends IMemberAssets | NewMemberAssets> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestMemberAssets = RestOf<IMemberAssets>;

export type NewRestMemberAssets = RestOf<NewMemberAssets>;

export type PartialUpdateRestMemberAssets = RestOf<PartialUpdateMemberAssets>;

export type EntityResponseType = HttpResponse<IMemberAssets>;
export type EntityArrayResponseType = HttpResponse<IMemberAssets[]>;

@Injectable({ providedIn: 'root' })
export class MemberAssetsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/member-assets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(memberAssets: NewMemberAssets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberAssets);
    return this.http
      .post<RestMemberAssets>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(memberAssets: IMemberAssets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberAssets);
    return this.http
      .put<RestMemberAssets>(`${this.resourceUrl}/${this.getMemberAssetsIdentifier(memberAssets)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(memberAssets: PartialUpdateMemberAssets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberAssets);
    return this.http
      .patch<RestMemberAssets>(`${this.resourceUrl}/${this.getMemberAssetsIdentifier(memberAssets)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMemberAssets>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMemberAssets[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMemberAssetsIdentifier(memberAssets: Pick<IMemberAssets, 'id'>): number {
    return memberAssets.id;
  }

  compareMemberAssets(o1: Pick<IMemberAssets, 'id'> | null, o2: Pick<IMemberAssets, 'id'> | null): boolean {
    return o1 && o2 ? this.getMemberAssetsIdentifier(o1) === this.getMemberAssetsIdentifier(o2) : o1 === o2;
  }

  addMemberAssetsToCollectionIfMissing<Type extends Pick<IMemberAssets, 'id'>>(
    memberAssetsCollection: Type[],
    ...memberAssetsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const memberAssets: Type[] = memberAssetsToCheck.filter(isPresent);
    if (memberAssets.length > 0) {
      const memberAssetsCollectionIdentifiers = memberAssetsCollection.map(
        memberAssetsItem => this.getMemberAssetsIdentifier(memberAssetsItem)!
      );
      const memberAssetsToAdd = memberAssets.filter(memberAssetsItem => {
        const memberAssetsIdentifier = this.getMemberAssetsIdentifier(memberAssetsItem);
        if (memberAssetsCollectionIdentifiers.includes(memberAssetsIdentifier)) {
          return false;
        }
        memberAssetsCollectionIdentifiers.push(memberAssetsIdentifier);
        return true;
      });
      return [...memberAssetsToAdd, ...memberAssetsCollection];
    }
    return memberAssetsCollection;
  }

  protected convertDateFromClient<T extends IMemberAssets | NewMemberAssets | PartialUpdateMemberAssets>(memberAssets: T): RestOf<T> {
    return {
      ...memberAssets,
      lastModified: memberAssets.lastModified?.toJSON() ?? null,
      createdOn: memberAssets.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMemberAssets: RestMemberAssets): IMemberAssets {
    return {
      ...restMemberAssets,
      lastModified: restMemberAssets.lastModified ? dayjs(restMemberAssets.lastModified) : undefined,
      createdOn: restMemberAssets.createdOn ? dayjs(restMemberAssets.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMemberAssets>): HttpResponse<IMemberAssets> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMemberAssets[]>): HttpResponse<IMemberAssets[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
