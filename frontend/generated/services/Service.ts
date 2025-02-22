/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class Service {
  /**
   * 用户信息
   * @returns string OK
   * @throws ApiError
   */
  public static test(): CancelablePromise<string> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/user/info"
    });
  }

  /**
   * 你好
   * @returns any OK
   * @throws ApiError
   */
  public static test2(): CancelablePromise<Record<string, any>> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/user/hello"
    });
  }
}
