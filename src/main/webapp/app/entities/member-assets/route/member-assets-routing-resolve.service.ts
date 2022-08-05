import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMemberAssets } from '../member-assets.model';
import { MemberAssetsService } from '../service/member-assets.service';

@Injectable({ providedIn: 'root' })
export class MemberAssetsRoutingResolveService implements Resolve<IMemberAssets | null> {
  constructor(protected service: MemberAssetsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMemberAssets | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((memberAssets: HttpResponse<IMemberAssets>) => {
          if (memberAssets.body) {
            return of(memberAssets.body);
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
