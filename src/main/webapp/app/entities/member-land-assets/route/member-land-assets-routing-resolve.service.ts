import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMemberLandAssets } from '../member-land-assets.model';
import { MemberLandAssetsService } from '../service/member-land-assets.service';

@Injectable({ providedIn: 'root' })
export class MemberLandAssetsRoutingResolveService implements Resolve<IMemberLandAssets | null> {
  constructor(protected service: MemberLandAssetsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMemberLandAssets | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((memberLandAssets: HttpResponse<IMemberLandAssets>) => {
          if (memberLandAssets.body) {
            return of(memberLandAssets.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
