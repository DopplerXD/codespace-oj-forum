/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseJSONObject } from "../models/BaseResponseJSONObject";
import type { BaseResponseJudgeVO } from "../models/BaseResponseJudgeVO";
import type { BaseResponseLong } from "../models/BaseResponseLong";
import type { JudgeQueryDto } from "../models/JudgeQueryDto";
import type { JudgeSubmitDto } from "../models/JudgeSubmitDto";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class JudgeControllerService {
  /**
   * @deprecated
   * @param requestBody
   * @returns BaseResponseLong OK
   * @throws ApiError
   */
  public static submit(
    requestBody: JudgeSubmitDto
  ): CancelablePromise<BaseResponseLong> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/submit",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @deprecated
   * @param judgeQueryDto
   * @returns BaseResponseJSONObject OK
   * @throws ApiError
   */
  public static judgeGetList1(
    judgeQueryDto: JudgeQueryDto
  ): CancelablePromise<BaseResponseJSONObject> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/list",
      query: {
        judgeQueryDto: judgeQueryDto,
      },
    });
  }

  /**
   * @deprecated
   * @param judgeId
   * @returns BaseResponseJudgeVO OK
   * @throws ApiError
   */
  public static judgeGet1(
    judgeId: number
  ): CancelablePromise<BaseResponseJudgeVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/get/{judgeId}",
      path: {
        judgeId: judgeId,
      },
    });
  }
}
