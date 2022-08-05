import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParameterLookup, NewParameterLookup } from '../parameter-lookup.model';

export type PartialUpdateParameterLookup = Partial<IParameterLookup> & Pick<IParameterLookup, 'id'>;

type RestOf<T extends IParameterLookup | NewParameterLookup> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestParameterLookup = RestOf<IParameterLookup>;

export type NewRestParameterLookup = RestOf<NewParameterLookup>;

export type PartialUpdateRestParameterLookup = RestOf<PartialUpdateParameterLookup>;

export type EntityResponseType = HttpResponse<IParameterLookup>;
export type EntityArrayResponseType = HttpResponse<IParameterLookup[]>;

@Injectable({ providedIn: 'root' })
export class ParameterLookupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parameter-lookups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(parameterLookup: NewParameterLookup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parameterLookup);
    return this.http
      .post<RestParameterLookup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(parameterLookup: IParameterLookup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parameterLookup);
    return this.http
      .put<RestParameterLookup>(`${this.resourceUrl}/${this.getParameterLookupIdentifier(parameterLookup)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(parameterLookup: PartialUpdateParameterLookup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parameterLookup);
    return this.http
      .patch<RestParameterLookup>(`${this.resourceUrl}/${this.getParameterLookupIdentifier(parameterLookup)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestParameterLookup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestParameterLookup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getParameterLookupIdentifier(parameterLookup: Pick<IParameterLookup, 'id'>): number {
    return parameterLookup.id;
  }

  compareParameterLookup(o1: Pick<IParameterLookup, 'id'> | null, o2: Pick<IParameterLookup, 'id'> | null): boolean {
    return o1 && o2 ? this.getParameterLookupIdentifier(o1) === this.getParameterLookupIdentifier(o2) : o1 === o2;
  }

  addParameterLookupToCollectionIfMissing<Type extends Pick<IParameterLookup, 'id'>>(
    parameterLookupCollection: Type[],
    ...parameterLookupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const parameterLookups: Type[] = parameterLookupsToCheck.filter(isPresent);
    if (parameterLookups.length > 0) {
      const parameterLookupCollectionIdentifiers = parameterLookupCollection.map(
        parameterLookupItem => this.getParameterLookupIdentifier(parameterLookupItem)!
      );
      const parameterLookupsToAdd = parameterLookups.filter(parameterLookupItem => {
        const parameterLookupIdentifier = this.getParameterLookupIdentifier(parameterLookupItem);
        if (parameterLookupCollectionIdentifiers.includes(parameterLookupIdentifier)) {
          return false;
        }
        parameterLookupCollectionIdentifiers.push(parameterLookupIdentifier);
        return true;
      });
      return [...parameterLookupsToAdd, ...parameterLookupCollection];
    }
    return parameterLookupCollection;
  }

  protected convertDateFromClient<T extends IParameterLookup | NewParameterLookup | PartialUpdateParameterLookup>(
    parameterLookup: T
  ): RestOf<T> {
    return {
      ...parameterLookup,
      lastModified: parameterLookup.lastModified?.toJSON() ?? null,
      createdOn: parameterLookup.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restParameterLookup: RestParameterLookup): IParameterLookup {
    return {
      ...restParameterLookup,
      lastModified: restParameterLookup.lastModified ? dayjs(restParameterLookup.lastModified) : undefined,
      createdOn: restParameterLookup.createdOn ? dayjs(restParameterLookup.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestParameterLookup>): HttpResponse<IParameterLookup> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestParameterLookup[]>): HttpResponse<IParameterLookup[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
