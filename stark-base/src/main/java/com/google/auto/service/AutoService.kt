/*
 * Copyright (C) 2008 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.auto.service

import java.lang.annotation.Documented
import kotlin.reflect.KClass

/**
 * copied from google auto-service, avoiding including that lib
 * @author huangjun.barney
 * @since 2020-05-18
 */


/**
 * An annotation for service providers as described in [java.util.ServiceLoader]. The [ ] generates the configuration files which
 * allows service providers to be loaded with [java.util.ServiceLoader.load].
 *
 *
 * Service providers assert that they conform to the service provider specification.
 * Specifically, they must:
 *
 *
 *  * be a non-inner, non-anonymous, concrete class
 *  * have a publicly accessible no-arg constructor
 *  * implement the interface type returned by `value()`
 *
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS
)
annotation class AutoService(
    /** Returns the interface implemented by this service provider.  */
    val value: KClass<*>
)