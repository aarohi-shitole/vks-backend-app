import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParameterLookup } from '../parameter-lookup.model';
import { ParameterLookupService } from '../service/parameter-lookup.service';

@Injectable({ providedIn: 'root' })
export class ParameterLookupRoutingResolveService implements Resolve<IParameterLookup | null> {
  constructor(protected service: ParameterLookupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParameterLookup | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((parameterLookup: HttpResponse<IParameterLookup>) => {
          if (parameterLookup.body) {
            return of(parameterLookup.body);
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
