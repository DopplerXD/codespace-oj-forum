/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseLong } from "../models/BaseResponseLong";
import type { ProblemCreateDto } from "../models/ProblemCreateDto";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class ProblemControllerService {
  /**
   * @param requestBody
   * @returns BaseResponseLong OK
   * @throws ApiError
   */
  public static problemCreate(
    requestBody: ProblemCreateDto
  ): CancelablePromise<BaseResponseLong> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/problem/create",
      body: requestBody,
      mediaType: "application/json"
    });
  }
}
