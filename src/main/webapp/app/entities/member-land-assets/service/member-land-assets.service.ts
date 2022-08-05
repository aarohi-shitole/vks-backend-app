import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMemberLandAssets, NewMemberLandAssets } from '../member-land-assets.model';

export type PartialUpdateMemberLandAssets = Partial<IMemberLandAssets> & Pick<IMemberLandAssets, 'id'>;

type RestOf<T extends IMemberLandAssets | NewMemberLandAssets> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestMemberLandAssets = RestOf<IMemberLandAssets>;

export type NewRestMemberLandAssets = RestOf<NewMemberLandAssets>;

export type PartialUpdateRestMemberLandAssets = RestOf<PartialUpdateMemberLandAssets>;

export type EntityResponseType = HttpResponse<IMemberLandAssets>;
export type EntityArrayResponseType = HttpResponse<IMemberLandAssets[]>;

@Injectable({ providedIn: 'root' })
export class MemberLandAssetsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/member-land-assets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(memberLandAssets: NewMemberLandAssets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberLandAssets);
    return this.http
      .post<RestMemberLandAssets>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(memberLandAssets: IMemberLandAssets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberLandAssets);
    return this.http
      .put<RestMemberLandAssets>(`${this.resourceUrl}/${this.getMemberLandAssetsIdentifier(memberLandAssets)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(memberLandAssets: PartialUpdateMemberLandAssets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberLandAssets);
    return this.http
      .patch<RestMemberLandAssets>(`${this.resourceUrl}/${this.getMemberLandAssetsIdentifier(memberLandAssets)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMemberLandAssets>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMemberLandAssets[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMemberLandAssetsIdentifier(memberLandAssets: Pick<IMemberLandAssets, 'id'>): number {
    return memberLandAssets.id;
  }

  compareMemberLandAssets(o1: Pick<IMemberLandAssets, 'id'> | null, o2: Pick<IMemberLandAssets, 'id'> | null): boolean {
    return o1 && o2 ? this.getMemberLandAssetsIdentifier(o1) === this.getMemberLandAssetsIdentifier(o2) : o1 === o2;
  }

  addMemberLandAssetsToCollectionIfMissing<Type extends Pick<IMemberLandAssets, 'id'>>(
    memberLandAssetsCollection: Type[],
    ...memberLandAssetsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const memberLandAssets: Type[] = memberLandAssetsToCheck.filter(isPresent);
    if (memberLandAssets.length > 0) {
      const memberLandAssetsCollectionIdentifiers = memberLandAssetsCollection.map(
        memberLandAssetsItem => this.getMemberLandAssetsIdentifier(memberLandAssetsItem)!
      );
      const memberLandAssetsToAdd = memberLandAssets.filter(memberLandAssetsItem => {
        const memberLandAssetsIdentifier = this.getMemberLandAssetsIdentifier(memberLandAssetsItem);
        if (memberLandAssetsCollectionIdentifiers.includes(memberLandAssetsIdentifier)) {
          return false;
        }
        memberLandAssetsCollectionIdentifiers.push(memberLandAssetsIdentifier);
        return true;
      });
      return [...memberLandAssetsToAdd, ...memberLandAssetsCollection];
    }
    return memberLandAssetsCollection;
  }

  protected convertDateFromClient<T extends IMemberLandAssets | NewMemberLandAssets | PartialUpdateMemberLandAssets>(
    memberLandAssets: T
  ): RestOf<T> {
    return {
      ...memberLandAssets,
      lastModified: memberLandAssets.lastModified?.toJSON() ?? null,
      createdOn: memberLandAssets.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMemberLandAssets: RestMemberLandAssets): IMemberLandAssets {
    return {
      ...restMemberLandAssets,
      lastModified: restMemberLandAssets.lastModified ? dayjs(restMemberLandAssets.lastModified) : undefined,
      createdOn: restMemberLandAssets.createdOn ? dayjs(restMemberLandAssets.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMemberLandAssets>): HttpResponse<IMemberLandAssets> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMemberLandAssets[]>): HttpResponse<IMemberLandAssets[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
