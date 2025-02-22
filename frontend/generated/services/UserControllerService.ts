/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseLoginUserVO } from "../models/BaseResponseLoginUserVO";
import type { BaseResponseMapStringObject } from "../models/BaseResponseMapStringObject";
import type { BaseResponseString } from "../models/BaseResponseString";
import type { UserLoginDto } from "../models/UserLoginDto";
import type { UserRegisterDto } from "../models/UserRegisterDto";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class UserControllerService {
  /**
   * @param requestBody
   * @returns BaseResponseString OK
   * @throws ApiError
   */
  public static userRegister(
    requestBody: UserRegisterDto
  ): CancelablePromise<BaseResponseString> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/register",
      body: requestBody,
      mediaType: "application/json"
    });
  }

  /**
   * @param requestBody
   * @returns BaseResponseMapStringObject OK
   * @throws ApiError
   */
  public static userLogin(
    requestBody: UserLoginDto
  ): CancelablePromise<BaseResponseMapStringObject> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/login",
      body: requestBody,
      mediaType: "application/json"
    });
  }

  /**
   * @returns BaseResponseLoginUserVO OK
   * @throws ApiError
   */
  public static getLoginUser(): CancelablePromise<BaseResponseLoginUserVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/user/get/login"
    });
  }
}
