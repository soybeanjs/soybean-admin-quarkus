/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.system.interfaces.rest.dto.request

/**
 * [issue](https://github.com/quarkusio/quarkus/issues/20395)
 * [issue](https://youtrack.jetbrains.com/issue/KT-13228)
 */
interface ValidationGroups {
    interface OnCreate

    interface OnUpdate

    interface OnDelete
}
