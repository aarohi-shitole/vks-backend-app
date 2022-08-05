import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMemberBank } from '../member-bank.model';
import { MemberBankService } from '../service/member-bank.service';

@Injectable({ providedIn: 'root' })
export class MemberBankRoutingResolveService implements Resolve<IMemberBank | null> {
  constructor(protected service: MemberBankService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMemberBank | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((memberBank: HttpResponse<IMemberBank>) => {
          if (memberBank.body) {
            return of(memberBank.body);
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
