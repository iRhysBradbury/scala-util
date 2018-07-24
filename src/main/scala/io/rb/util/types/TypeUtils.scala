package io.rb.util.types

import io.rb.util.types.t.TUtils
import io.rb.util.types.trys.TryUtils
import io.rb.util.types.eithers.EitherUtils
import io.rb.util.types.futures.FutureUtils

trait TypeUtils
  extends TUtils
    with TryUtils
    with EitherUtils
    with FutureUtils