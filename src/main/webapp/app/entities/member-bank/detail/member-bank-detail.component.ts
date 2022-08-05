import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMemberBank } from '../member-bank.model';

@Component({
  selector: 'jhi-member-bank-detail',
  templateUrl: './member-bank-detail.component.html',
})
export class MemberBankDetailComponent implements OnInit {
  memberBank: IMemberBank | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberBank }) => {
      this.memberBank = memberBank;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
