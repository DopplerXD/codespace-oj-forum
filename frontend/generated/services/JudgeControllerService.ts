/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseLong } from "../models/BaseResponseLong";
import type { JudgeSubmitDto } from "../models/JudgeSubmitDto";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class JudgeControllerService {
  /**
   * @param requestBody
   * @returns BaseResponseLong OK
   * @throws ApiError
   */
  public static submit(
    requestBody: JudgeSubmitDto
  ): CancelablePromise<BaseResponseLong> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/judge/submit",
      body: requestBody,
      mediaType: "application/json"
    });
  }
}
