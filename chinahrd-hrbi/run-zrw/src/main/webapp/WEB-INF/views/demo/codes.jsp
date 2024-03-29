﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>才报平台编码规范实例</title>
    <link rel="stylesheet" href="${ctx}/assets/css/docs.min.css">
</head>
<body>
<div class="bs-docs-header" id="content">
    <div class="container">
        <h1>才报平台编码规范实例</h1>
    </div>
</div>
<div class="container bs-docs-container">

    <div class="row">
        <div class="col-md-9" role="main">
            <div class="bs-docs-section">
                <h2 id="overview" class="page-header">前言</h2>

                <p class="lead">本平台以 Bootstrap 为底层前端框架，所以在实现功能的过程中请严格遵循<a href="http://codeguide.bootcss.com/" target="_blank">Bootstrap 编码规范</a>。</p>
                <p class="lead">新功能开发的默认页面以项目内的<code>default.jsp</code>为例。</p>


                <h3 id="overview-doctype">HTML5 文档类型</h3>
                <p>Bootstrap 使用到的某些 HTML 元素和 CSS 属性需要将页面设置为 HTML5 文档类型。在你项目中的每个页面都要参照下面的格式进行设置。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="cp">&lt;!DOCTYPE html&gt;</span>
<span class="nt">&lt;html</span> <span class="na">lang=</span><span class="s">"zh-CN"</span><span class="nt">&gt;</span>
  ...
<span class="nt">&lt;/html&gt;</span>
</code></pre></div>

                <h3 id="overview-mobile">移动设备优先</h3>
                <p>在 Bootstrap 2 中，我们对框架中的某些关键部分增加了对移动设备友好的样式。而在 Bootstrap 3 中，我们重写了整个框架，使其一开始就是对移动设备友好的。这次不是简单的增加一些可选的针对移动设备的样式，而是直接融合进了框架的内核中。也就是说，<strong>Bootstrap 是移动设备优先的</strong>。针对移动设备的样式融合进了框架的每个角落，而不是增加一个额外的文件。</p>
                <p>为了确保适当的绘制和触屏缩放，需要在 <code>&lt;head&gt;</code> 之中<strong>添加 viewport 元数据标签</strong>。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;meta</span> <span class="na">name=</span><span class="s">"viewport"</span> <span class="na">content=</span><span class="s">"width=device-width, initial-scale=1"</span><span class="nt">&gt;</span>
</code></pre></div>
                <p>在移动设备浏览器上，通过为视口（viewport）设置 meta 属性为 <code>user-scalable=no</code> 可以禁用其缩放（zooming）功能。这样禁用缩放功能后，用户只能滚动屏幕，就能让你的网站看上去更像原生应用的感觉。注意，这种方式我们并不推荐所有网站使用，还是要看你自己的情况而定！</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;meta</span> <span class="na">name=</span><span class="s">"viewport"</span> <span class="na">content=</span><span class="s">"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"</span><span class="nt">&gt;</span>
</code></pre></div>

                <h3 id="overview-type-links">排版与链接</h3>
                <p>Bootstrap 排版、链接样式设置了基本的全局样式。分别是：</p>
                <ul>
                    <li>为 <code>body</code> 元素设置 <code>background-color: #fff;</code></li>
                    <li>使用 <code>@font-family-base</code>、<code>@font-size-base</code> 和 <code>@line-height-base</code> a变量作为排版的基本参数</li>
                    <li>为所有链接设置了基本颜色 <code>@link-color</code> ，并且当链接处于 <code>:hover</code> 状态时才添加下划线</li>
                </ul>
                <p>这些样式都能在 <code>scaffolding.less</code> 文件中找到对应的源码。</p>

                <h3 id="overview-normalize">Normalize.css</h3>
                <p>为了增强跨浏览器表现的一致性，我们使用了 <a href="http://necolas.github.io/normalize.css/" target="_blank">Normalize.css</a>，这是由 <a href="https://twitter.com/necolas" target="_blank">Nicolas Gallagher</a> 和 <a href="https://twitter.com/jon_neal" target="_blank">Jonathan Neal</a> 维护的一个CSS 重置样式库。</p>

                <h3 id="overview-container">布局容器</h3>
                <p>Bootstrap 需要为页面内容和栅格系统包裹一个 <code>.container</code> 容器。我们提供了两个作此用处的类。注意，由于 <code>padding</code> 等属性的原因，这两种 容器类不能互相嵌套。</p>
                <p><code>.container</code> 类用于固定宽度并支持响应式布局的容器。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"container"</span><span class="nt">&gt;</span>
  ...
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>
                <p><code>.container-fluid</code> 类用于 100% 宽度，占据全部视口（viewport）的容器。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"container-fluid"</span><span class="nt">&gt;</span>
  ...
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>
            </div>

            <div class="bs-docs-section">
                <h1 id="grid" class="page-header">栅格系统</h1>

                <p class="lead">Bootstrap 提供了一套响应式、移动设备优先的流式栅格系统，随着屏幕或视口（viewport）尺寸的增加，系统会自动分为最多12列。它包含了易于使用的<a href="#grid-example-basic">预定义类</a>，还有强大的<a href="#grid-less">mixin 用于生成更具语义的布局</a>。</p>

                <h3 id="grid-intro">简介</h3>
                <p>栅格系统用于通过一系列的行（row）与列（column）的组合来创建页面布局，你的内容就可以放入这些创建好的布局中。下面就介绍一下 Bootstrap 栅格系统的工作原理：</p>
                <ul>
                    <li>“行（row）”必须包含在 <code>.container</code> （固定宽度）或 <code>.container-fluid</code> （100% 宽度）中，以便为其赋予合适的排列（aligment）和内补（padding）。</li>
                    <li>通过“行（row）”在水平方向创建一组“列（column）”。</li>
                    <li>你的内容应当放置于“列（column）”内，并且，只有“列（column）”可以作为行（row）”的直接子元素。</li>
                    <li>类似 <code>.row</code> 和 <code>.col-xs-4</code> 这种预定义的类，可以用来快速创建栅格布局。Bootstrap 源码中定义的 mixin 也可以用来创建语义化的布局。</li>
                    <li>通过为“列（column）”设置 <code>padding</code> 属性，从而创建列与列之间的间隔（gutter）。通过为 <code>.row</code> 元素设置负值 <code>margin</code> 从而抵消掉为 <code>.container</code> 元素设置的 <code>padding</code>，也就间接为“行（row）”所包含的“列（column）”抵消掉了<code>padding</code>。</li>
                    <li>The negative margin is why the examples below are outdented. It's so that content within grid columns is lined up with non-grid content.</li>
                    <li>Grid columns are created by specifying the number of twelve available columns you wish to span. For example, three equal columns would use three <code>.col-xs-4</code>.</li>
                    <li>如果一“行（row）”中包含了的“列（column）”大于 12，多余的“列（column）”所在的元素将被作为一个整体另起一行排列。</li>
                    <li>Grid classes apply to devices with screen widths greater than or equal to the breakpoint sizes, and override grid classes targeted at smaller devices. Therefore, applying any <code>.col-md-</code> class to an element will not only affect its styling on medium devices but also on large devices if a <code>.col-lg-</code> class is not present.</li>
                </ul>
                <p>通过研究后面的实例，可以将这些原理应用到你的代码中。</p>

                <h3 id="grid-media-queries">媒体查询</h3>
                <p>在栅格系统中，我们在 Less 文件中使用以下媒体查询（media query）来创建关键的分界点阈值。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="cm">/* 超小屏幕（手机，小于 768px） */</span>
<span class="cm">/* 没有任何媒体查询相关的代码，因为这在 Bootstrap 中是默认的（还记得 Bootstrap 是移动设备优先的吗？） */</span>

<span class="cm">/* 小屏幕（平板，大于等于 768px） */</span>
<span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-sm-min</span><span class="o">)</span> <span class="p">{</span> <span class="nc">...</span> <span class="p">}</span>

<span class="cm">/* 中等屏幕（桌面显示器，大于等于 992px） */</span>
<span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-md-min</span><span class="o">)</span> <span class="p">{</span> <span class="nc">...</span> <span class="p">}</span>

<span class="cm">/* 大屏幕（大桌面显示器，大于等于 1200px） */</span>
<span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-lg-min</span><span class="o">)</span> <span class="p">{</span> <span class="nc">...</span> <span class="p">}</span>
</code></pre></div>
                <p>我们偶尔也会在媒体查询代码中包含 <code>max-width</code> 从而将 CSS 的影响限制在更小范围的屏幕大小之内。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="k">@media</span> <span class="o">(</span><span class="nt">max-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-xs-max</span><span class="o">)</span> <span class="p">{</span> <span class="nc">...</span> <span class="p">}</span>
<span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-sm-min</span><span class="o">)</span> <span class="nt">and</span> <span class="o">(</span><span class="nt">max-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-sm-max</span><span class="o">)</span> <span class="p">{</span> <span class="nc">...</span> <span class="p">}</span>
<span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-md-min</span><span class="o">)</span> <span class="nt">and</span> <span class="o">(</span><span class="nt">max-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-md-max</span><span class="o">)</span> <span class="p">{</span> <span class="nc">...</span> <span class="p">}</span>
<span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-lg-min</span><span class="o">)</span> <span class="p">{</span> <span class="nc">...</span> <span class="p">}</span>
</code></pre></div>

                <h3 id="grid-options">栅格参数</h3>
                <p>通过下表可以详细查看 Bootstrap 的栅格系统是如何在多种屏幕设备上工作的。</p>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th></th>
                            <th>
                                超小屏幕
                                <small>手机 (&lt;768px)</small>
                            </th>
                            <th>
                                小屏幕
                                <small>平板 (≥768px)</small>
                            </th>
                            <th>
                                中等屏幕
                                <small>桌面显示器 (≥992px)</small>
                            </th>
                            <th>
                                大屏幕
                                <small>大桌面显示器 (≥1200px)</small>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th class="text-nowrap">栅格系统行为</th>
                            <td>总是水平排列</td>
                            <td colspan="3">开始是堆叠在一起的，当大于这些阈值时将变为水平排列C</td>
                        </tr>
                        <tr>
                            <th class="text-nowrap"><code>.container</code> 最大宽度</th>
                            <td>None （自动）</td>
                            <td>750px</td>
                            <td>970px</td>
                            <td>1170px</td>
                        </tr>
                        <tr>
                            <th class="text-nowrap">类前缀</th>
                            <td><code>.col-xs-</code></td>
                            <td><code>.col-sm-</code></td>
                            <td><code>.col-md-</code></td>
                            <td><code>.col-lg-</code></td>
                        </tr>
                        <tr>
                            <th class="text-nowrap">列（column）数</th>
                            <td colspan="4">12</td>
                        </tr>
                        <tr>
                            <th class="text-nowrap">最大列（column）宽</th>
                            <td class="text-muted">自动</td>
                            <td>~62px</td>
                            <td>~81px</td>
                            <td>~97px</td>
                        </tr>
                        <tr>
                            <th class="text-nowrap">槽（gutter）宽</th>
                            <td colspan="4">30px （每列左右均有 15px）</td>
                        </tr>
                        <tr>
                            <th class="text-nowrap">可嵌套</th>
                            <td colspan="4">是</td>
                        </tr>
                        <tr>
                            <th class="text-nowrap">偏移（Offsets）</th>
                            <td colspan="4">是</td>
                        </tr>
                        <tr>
                            <th class="text-nowrap">列排序</th>
                            <td colspan="4">是</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <h3 id="grid-example-basic">实例：从堆叠到水平排列</h3>
                <p>使用单一的一组 <code>.col-md-*</code> 栅格类，就可以创建一个基本的栅格系统，在手机和平板设备上一开始是堆叠在一起的（超小屏幕到小屏幕这一范围），在桌面（中等）屏幕设备上变为水平排列。所有“列（column）必须放在 ” <code>.row</code> 内。</p>
                <div class="bs-docs-grid">
                    <div class="row show-grid">
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                        <div class="col-md-1">.col-md-1</div>
                    </div>
                    <div class="row show-grid">
                        <div class="col-md-8">.col-md-8</div>
                        <div class="col-md-4">.col-md-4</div>
                    </div>
                    <div class="row show-grid">
                        <div class="col-md-4">.col-md-4</div>
                        <div class="col-md-4">.col-md-4</div>
                        <div class="col-md-4">.col-md-4</div>
                    </div>
                    <div class="row show-grid">
                        <div class="col-md-6">.col-md-6</div>
                        <div class="col-md-6">.col-md-6</div>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-1"</span><span class="nt">&gt;</span>.col-md-1<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-8"</span><span class="nt">&gt;</span>.col-md-8<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-4"</span><span class="nt">&gt;</span>.col-md-4<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-4"</span><span class="nt">&gt;</span>.col-md-4<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-4"</span><span class="nt">&gt;</span>.col-md-4<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-4"</span><span class="nt">&gt;</span>.col-md-4<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-6"</span><span class="nt">&gt;</span>.col-md-6<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-6"</span><span class="nt">&gt;</span>.col-md-6<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h3 id="grid-example-fluid">实例：流式布局容器</h3>
                <p>将最外面的布局元素 <code>.container</code> 修改为 <code>.container-fluid</code>，就可以将固定宽度的栅格布局转换为 100% 宽度的布局。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"container-fluid"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
    ...
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h3 id="grid-example-mixed">实例：移动设备和桌面屏幕</h3>
                <p>是否不希望在小屏幕设备上所有列都堆叠在一起？那就使用针对超小屏幕和中等屏幕设备所定义的类吧，即 <code>.col-xs-*</code> 和 <code>.col-md-*</code>。请看下面的实例，研究一下这些是如何工作的。</p>
                <div class="bs-docs-grid">
                    <div class="row show-grid">
                        <div class="col-xs-12 col-md-8">.col-xs-12 .col-md-8</div>
                        <div class="col-xs-6 col-md-4">.col-xs-6 .col-md-4</div>
                    </div>
                    <div class="row show-grid">
                        <div class="col-xs-6 col-md-4">.col-xs-6 .col-md-4</div>
                        <div class="col-xs-6 col-md-4">.col-xs-6 .col-md-4</div>
                        <div class="col-xs-6 col-md-4">.col-xs-6 .col-md-4</div>
                    </div>
                    <div class="row show-grid">
                        <div class="col-xs-6">.col-xs-6</div>
                        <div class="col-xs-6">.col-xs-6</div>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="c">&lt;!-- Stack the columns on mobile by making one full-width and the other half-width --&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-12 col-md-8"</span><span class="nt">&gt;</span>.col-xs-12 .col-md-8<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-md-4"</span><span class="nt">&gt;</span>.col-xs-6 .col-md-4<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>

<span class="c">&lt;!-- Columns start at 50% wide on mobile and bump up to 33.3% wide on desktop --&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-md-4"</span><span class="nt">&gt;</span>.col-xs-6 .col-md-4<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-md-4"</span><span class="nt">&gt;</span>.col-xs-6 .col-md-4<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-md-4"</span><span class="nt">&gt;</span>.col-xs-6 .col-md-4<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>

<span class="c">&lt;!-- Columns are always 50% wide, on mobile and desktop --&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6"</span><span class="nt">&gt;</span>.col-xs-6<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6"</span><span class="nt">&gt;</span>.col-xs-6<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h3 id="grid-example-mixed-complete">实例：手机、平板、桌面</h3>
                <p>在上面案例的基础上，通过使用针对平板设备的 <code>.col-sm-*</code> 类，我们来创建更加动态和强大的布局吧。</p>
                <div class="bs-docs-grid">
                    <div class="row show-grid">
                        <div class="col-xs-12 col-sm-6 col-md-8">.col-xs-12 .col-sm-6 .col-md-8</div>
                        <div class="col-xs-6 col-md-4">.col-xs-6 .col-md-4</div>
                    </div>
                    <div class="row show-grid">
                        <div class="col-xs-6 col-sm-4">.col-xs-6 .col-sm-4</div>
                        <div class="col-xs-6 col-sm-4">.col-xs-6 .col-sm-4</div>
                        <!-- Optional: clear the XS cols if their content doesn't match in height -->
                        <div class="clearfix visible-xs-block"></div>
                        <div class="col-xs-6 col-sm-4">.col-xs-6 .col-sm-4</div>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-12 col-sm-6 col-md-8"</span><span class="nt">&gt;</span>.col-xs-12 .col-sm-6 .col-md-8<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-md-4"</span><span class="nt">&gt;</span>.col-xs-6 .col-md-4<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-sm-4"</span><span class="nt">&gt;</span>.col-xs-6 .col-sm-4<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-sm-4"</span><span class="nt">&gt;</span>.col-xs-6 .col-sm-4<span class="nt">&lt;/div&gt;</span>
  <span class="c">&lt;!-- Optional: clear the XS cols if their content doesn't match in height --&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"clearfix visible-xs-block"</span><span class="nt">&gt;&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-sm-4"</span><span class="nt">&gt;</span>.col-xs-6 .col-sm-4<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h3 id="grid-example-wrapping">实例：多余的列（column）将另起一行排列</h3>
                <p>如果在一个 <code>.row</code> 内包含的列（column）大于12个，包含多余列（column）的元素将作为一个整体单元被另起一行排列。</p>
                <div class="bs-docs-grid">
                    <div class="row show-grid">
                        <div class="col-xs-9">.col-xs-9</div>
                        <div class="col-xs-4">.col-xs-4<br>Since 9 + 4 = 13 &gt; 12, this 4-column-wide div gets wrapped onto a new line as one contiguous unit.</div>
                        <div class="col-xs-6">.col-xs-6<br>Subsequent columns continue along the new line.</div>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-9"</span><span class="nt">&gt;</span>.col-xs-9<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-4"</span><span class="nt">&gt;</span>.col-xs-4<span class="nt">&lt;br&gt;</span>Since 9 + 4 = 13 <span class="ni">&amp;gt;</span> 12, this 4-column-wide div gets wrapped onto a new line as one contiguous unit.<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6"</span><span class="nt">&gt;</span>.col-xs-6<span class="nt">&lt;br&gt;</span>Subsequent columns continue along the new line.<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h3 id="grid-responsive-resets">Responsive column resets</h3>
                <p>With the four tiers of grids available you're bound to run into issues where, at certain breakpoints, your columns don't clear quite right as one is taller than the other. To fix that, use a combination of a <code>.clearfix</code> and our <a href="#responsive-utilities">responsive utility classes</a>.</p>
                <div class="bs-docs-grid">
                    <div class="row show-grid">
                        <div class="col-xs-6 col-sm-3">
                            .col-xs-6 .col-sm-3
                            <br>
                            Resize your viewport or check it out on your phone for an example.
                        </div>
                        <div class="col-xs-6 col-sm-3">.col-xs-6 .col-sm-3</div>

                        <!-- Add the extra clearfix for only the required viewport -->
                        <div class="clearfix visible-xs-block"></div>

                        <div class="col-xs-6 col-sm-3">.col-xs-6 .col-sm-3</div>
                        <div class="col-xs-6 col-sm-3">.col-xs-6 .col-sm-3</div>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-sm-3"</span><span class="nt">&gt;</span>.col-xs-6 .col-sm-3<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-sm-3"</span><span class="nt">&gt;</span>.col-xs-6 .col-sm-3<span class="nt">&lt;/div&gt;</span>

  <span class="c">&lt;!-- Add the extra clearfix for only the required viewport --&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"clearfix visible-xs-block"</span><span class="nt">&gt;&lt;/div&gt;</span>

  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-sm-3"</span><span class="nt">&gt;</span>.col-xs-6 .col-sm-3<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-6 col-sm-3"</span><span class="nt">&gt;</span>.col-xs-6 .col-sm-3<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>
                <p>In addition to column clearing at responsive breakpoints, you may need to <strong>reset offsets, pushes, or pulls</strong>. See this in action in <a href="../examples/grid/">the grid example</a>.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-5 col-md-6"</span><span class="nt">&gt;</span>.col-sm-5 .col-md-6<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-5 col-sm-offset-2 col-md-6 col-md-offset-0"</span><span class="nt">&gt;</span>.col-sm-5 .col-sm-offset-2 .col-md-6 .col-md-offset-0<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>

<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-6 col-md-5 col-lg-6"</span><span class="nt">&gt;</span>.col-sm-6 .col-md-5 .col-lg-6<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-6 col-md-5 col-md-offset-2 col-lg-6 col-lg-offset-0"</span><span class="nt">&gt;</span>.col-sm-6 .col-md-5 .col-md-offset-2 .col-lg-6 .col-lg-offset-0<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>


                <h3 id="grid-offsetting">列偏移</h3>
                <p>使用 <code>.col-md-offset-*</code> 类可以将列向右侧偏移。这些类实际是通过使用 <code>*</code> 选择器为当前元素增加了左侧的边距（margin）。例如，<code>.col-md-offset-4</code> 类将 <code>.col-md-4</code> 元素向右侧偏移了4个列（column）的宽度。</p>
                <div class="bs-docs-grid">
                    <div class="row show-grid">
                        <div class="col-md-4">.col-md-4</div>
                        <div class="col-md-4 col-md-offset-4">.col-md-4 .col-md-offset-4</div>
                    </div>
                    <div class="row show-grid">
                        <div class="col-md-3 col-md-offset-3">.col-md-3 .col-md-offset-3</div>
                        <div class="col-md-3 col-md-offset-3">.col-md-3 .col-md-offset-3</div>
                    </div>
                    <div class="row show-grid">
                        <div class="col-md-6 col-md-offset-3">.col-md-6 .col-md-offset-3</div>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-4"</span><span class="nt">&gt;</span>.col-md-4<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-4 col-md-offset-4"</span><span class="nt">&gt;</span>.col-md-4 .col-md-offset-4<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-3 col-md-offset-3"</span><span class="nt">&gt;</span>.col-md-3 .col-md-offset-3<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-3 col-md-offset-3"</span><span class="nt">&gt;</span>.col-md-3 .col-md-offset-3<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-6 col-md-offset-3"</span><span class="nt">&gt;</span>.col-md-6 .col-md-offset-3<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>


                <h3 id="grid-nesting">嵌套列</h3>
                <p>为了使用内置的栅格系统将内容再次嵌套，可以通过添加一个新的 <code>.row</code> 元素和一系列 <code>.col-sm-*</code> 元素到已经存在的 <code>.col-sm-*</code> 元素内。被嵌套的行（row）所包含的列（column）的个数不能超过12（其实，没有要求你必须占满12列）。</p>
                <div class="row show-grid">
                    <div class="col-sm-9">
                        Level 1: .col-sm-9
                        <div class="row show-grid">
                            <div class="col-xs-8 col-sm-6">
                                Level 2: .col-xs-8 .col-sm-6
                            </div>
                            <div class="col-xs-4 col-sm-6">
                                Level 2: .col-xs-4 .col-sm-6
                            </div>
                        </div>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-9"</span><span class="nt">&gt;</span>
    Level 1: .col-sm-9
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-8 col-sm-6"</span><span class="nt">&gt;</span>
        Level 2: .col-xs-8 .col-sm-6
      <span class="nt">&lt;/div&gt;</span>
      <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-4 col-sm-6"</span><span class="nt">&gt;</span>
        Level 2: .col-xs-4 .col-sm-6
      <span class="nt">&lt;/div&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h3 id="grid-column-ordering">列排序</h3>
                <p>通过使用 <code>.col-md-push-*</code> 和 <code>.col-md-pull-*</code> 类就可以很容易的改变列（column）的顺序。</p>
                <div class="row show-grid">
                    <div class="col-md-9 col-md-push-3">.col-md-9 .col-md-push-3</div>
                    <div class="col-md-3 col-md-pull-9">.col-md-3 .col-md-pull-9</div>
                </div>

                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-9 col-md-push-3"</span><span class="nt">&gt;</span>.col-md-9 .col-md-push-3<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-md-3 col-md-pull-9"</span><span class="nt">&gt;</span>.col-md-3 .col-md-pull-9<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h3 id="grid-less">Less mixin 和变量</h3>
                <p>除了用于快速布局的<a href="#grid-example-basic">预定义栅格类</a>，Bootstrap 还包含了一组 Less 变量和 mixin 用于帮你生成简单、语义化的布局。</p>

                <h4>变量</h4>
                <p>通过变量来定义列数、槽（gutter）宽、媒体查询阈值（用于确定合适让列浮动）。我们使用这些变量生成预定义的栅格类，如上所示，还有如下所示的定制 mixin。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="k">@grid-columns</span><span class="nd">:</span>              <span class="nt">12</span><span class="o">;</span>
<span class="o">@</span><span class="nt">grid-gutter-width</span><span class="nd">:</span>         <span class="nt">30px</span><span class="o">;</span>
<span class="o">@</span><span class="nt">grid-float-breakpoint</span><span class="nd">:</span>     <span class="nt">768px</span><span class="o">;</span>
</code></pre></div>

                <h4>mixin</h4>
                <p>mixin 用来和栅格变量一同使用，为每个列（column）生成语义化的 CSS 代码。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Creates a wrapper for a series of columns</span>
<span class="na">.make-row(@gutter</span><span class="o">:</span> <span class="o">@</span><span class="n">grid-gutter-width</span><span class="p">)</span> <span class="p">{</span>
  <span class="c1">// Then clear the floated columns</span>
  <span class="nc">.clearfix</span><span class="o">();</span>

  <span class="o">@</span><span class="nt">media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-sm-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">margin-left</span><span class="o">:</span>  <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">-2</span><span class="p">);</span>
    <span class="na">margin-right</span><span class="o">:</span> <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">-2</span><span class="p">);</span>
  <span class="p">}</span>

  <span class="c1">// Negative margin nested rows out to align the content of columns</span>
  <span class="nc">.row</span> <span class="p">{</span>
    <span class="na">margin-left</span><span class="o">:</span>  <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">-2</span><span class="p">);</span>
    <span class="na">margin-right</span><span class="o">:</span> <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">-2</span><span class="p">);</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Generate the extra small columns</span>
<span class="nc">.make-xs-column</span><span class="o">(@</span><span class="nt">columns</span><span class="o">;</span> <span class="o">@</span><span class="nt">gutter</span><span class="nd">:</span> <span class="o">@</span><span class="nt">grid-gutter-width</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">position</span><span class="o">:</span> <span class="no">relative</span><span class="p">;</span>
  <span class="c1">// Prevent columns from collapsing when empty</span>
  <span class="na">min-height</span><span class="o">:</span> <span class="mi">1</span><span class="kt">px</span><span class="p">;</span>
  <span class="c1">// Inner gutter via padding</span>
  <span class="na">padding-left</span><span class="o">:</span>  <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">2</span><span class="p">);</span>
  <span class="na">padding-right</span><span class="o">:</span> <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">2</span><span class="p">);</span>

  <span class="c1">// Calculate width based on number of columns available</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">grid-float-breakpoint</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">float</span><span class="o">:</span> <span class="no">left</span><span class="p">;</span>
    <span class="na">width</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Generate the small columns</span>
<span class="nc">.make-sm-column</span><span class="o">(@</span><span class="nt">columns</span><span class="o">;</span> <span class="o">@</span><span class="nt">gutter</span><span class="nd">:</span> <span class="o">@</span><span class="nt">grid-gutter-width</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">position</span><span class="o">:</span> <span class="no">relative</span><span class="p">;</span>
  <span class="c1">// Prevent columns from collapsing when empty</span>
  <span class="na">min-height</span><span class="o">:</span> <span class="mi">1</span><span class="kt">px</span><span class="p">;</span>
  <span class="c1">// Inner gutter via padding</span>
  <span class="na">padding-left</span><span class="o">:</span>  <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">2</span><span class="p">);</span>
  <span class="na">padding-right</span><span class="o">:</span> <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">2</span><span class="p">);</span>

  <span class="c1">// Calculate width based on number of columns available</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-sm-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">float</span><span class="o">:</span> <span class="no">left</span><span class="p">;</span>
    <span class="na">width</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Generate the small column offsets</span>
<span class="nc">.make-sm-column-offset</span><span class="o">(@</span><span class="nt">columns</span><span class="o">)</span> <span class="p">{</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-sm-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">margin-left</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>
<span class="nc">.make-sm-column-push</span><span class="o">(@</span><span class="nt">columns</span><span class="o">)</span> <span class="p">{</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-sm-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">left</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>
<span class="nc">.make-sm-column-pull</span><span class="o">(@</span><span class="nt">columns</span><span class="o">)</span> <span class="p">{</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-sm-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">right</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Generate the medium columns</span>
<span class="nc">.make-md-column</span><span class="o">(@</span><span class="nt">columns</span><span class="o">;</span> <span class="o">@</span><span class="nt">gutter</span><span class="nd">:</span> <span class="o">@</span><span class="nt">grid-gutter-width</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">position</span><span class="o">:</span> <span class="no">relative</span><span class="p">;</span>
  <span class="c1">// Prevent columns from collapsing when empty</span>
  <span class="na">min-height</span><span class="o">:</span> <span class="mi">1</span><span class="kt">px</span><span class="p">;</span>
  <span class="c1">// Inner gutter via padding</span>
  <span class="na">padding-left</span><span class="o">:</span>  <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">2</span><span class="p">);</span>
  <span class="na">padding-right</span><span class="o">:</span> <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">2</span><span class="p">);</span>

  <span class="c1">// Calculate width based on number of columns available</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-md-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">float</span><span class="o">:</span> <span class="no">left</span><span class="p">;</span>
    <span class="na">width</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Generate the medium column offsets</span>
<span class="nc">.make-md-column-offset</span><span class="o">(@</span><span class="nt">columns</span><span class="o">)</span> <span class="p">{</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-md-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">margin-left</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>
<span class="nc">.make-md-column-push</span><span class="o">(@</span><span class="nt">columns</span><span class="o">)</span> <span class="p">{</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-md-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">left</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>
<span class="nc">.make-md-column-pull</span><span class="o">(@</span><span class="nt">columns</span><span class="o">)</span> <span class="p">{</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-md-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">right</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Generate the large columns</span>
<span class="nc">.make-lg-column</span><span class="o">(@</span><span class="nt">columns</span><span class="o">;</span> <span class="o">@</span><span class="nt">gutter</span><span class="nd">:</span> <span class="o">@</span><span class="nt">grid-gutter-width</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">position</span><span class="o">:</span> <span class="no">relative</span><span class="p">;</span>
  <span class="c1">// Prevent columns from collapsing when empty</span>
  <span class="na">min-height</span><span class="o">:</span> <span class="mi">1</span><span class="kt">px</span><span class="p">;</span>
  <span class="c1">// Inner gutter via padding</span>
  <span class="na">padding-left</span><span class="o">:</span>  <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">2</span><span class="p">);</span>
  <span class="na">padding-right</span><span class="o">:</span> <span class="p">(</span><span class="o">@</span><span class="n">gutter</span> <span class="o">/</span> <span class="mi">2</span><span class="p">);</span>

  <span class="c1">// Calculate width based on number of columns available</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-lg-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">float</span><span class="o">:</span> <span class="no">left</span><span class="p">;</span>
    <span class="na">width</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Generate the large column offsets</span>
<span class="nc">.make-lg-column-offset</span><span class="o">(@</span><span class="nt">columns</span><span class="o">)</span> <span class="p">{</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-lg-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">margin-left</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>
<span class="nc">.make-lg-column-push</span><span class="o">(@</span><span class="nt">columns</span><span class="o">)</span> <span class="p">{</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-lg-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">left</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>
<span class="nc">.make-lg-column-pull</span><span class="o">(@</span><span class="nt">columns</span><span class="o">)</span> <span class="p">{</span>
  <span class="k">@media</span> <span class="o">(</span><span class="nt">min-width</span><span class="nd">:</span> <span class="o">@</span><span class="nt">screen-lg-min</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">right</span><span class="o">:</span> <span class="nf">percentage</span><span class="p">((</span><span class="o">@</span><span class="n">columns</span> <span class="o">/</span> <span class="o">@</span><span class="n">grid-columns</span><span class="p">));</span>
  <span class="p">}</span>
<span class="p">}</span>
</code></pre></div>

                <h4>实例展示</h4>
                <p>你可以重新修改这些变量的值，或者用默认值调用这些 mixin。下面就是一个利用默认设置生成两列布局（列之间有间隔）的案例。</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nc">.wrapper</span> <span class="p">{</span>
  <span class="nc">.make-row</span><span class="o">();</span>
<span class="p">}</span>
<span class="nc">.content-main</span> <span class="p">{</span>
  <span class="nc">.make-lg-column</span><span class="o">(</span><span class="nt">8</span><span class="o">);</span>
<span class="p">}</span>
<span class="nc">.content-secondary</span> <span class="p">{</span>
  <span class="nc">.make-lg-column</span><span class="o">(</span><span class="nt">3</span><span class="o">);</span>
  <span class="nc">.make-lg-column-offset</span><span class="o">(</span><span class="nt">1</span><span class="o">);</span>
<span class="p">}</span>
</code></pre></div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"wrapper"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"content-main"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"content-secondary"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>
            </div>

            <div class="bs-docs-section">
                <h1 id="type" class="page-header">排版</h1>

                <!-- Headings -->
                <h2 id="type-headings">标题</h2>
                <p>HTML 中的所有标题标签，<code>&lt;h1&gt;</code> 到 <code>&lt;h6&gt;</code> 均可使用。另外，还提供了 <code>.h1</code> 到 <code>.h6</code> 类，为的是给内联（inline）属性的文本赋予标题的样式。</p>
                <div class="bs-example bs-example-type">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td><h1>h1. Bootstrap heading</h1></td>
                            <td class="type-info">Semibold 36px</td>
                        </tr>
                        <tr>
                            <td><h2>h2. Bootstrap heading</h2></td>
                            <td class="type-info">Semibold 30px</td>
                        </tr>
                        <tr>
                            <td><h3>h3. Bootstrap heading</h3></td>
                            <td class="type-info">Semibold 24px</td>
                        </tr>
                        <tr>
                            <td><h4>h4. Bootstrap heading</h4></td>
                            <td class="type-info">Semibold 18px</td>
                        </tr>
                        <tr>
                            <td><h5>h5. Bootstrap heading</h5></td>
                            <td class="type-info">Semibold 14px</td>
                        </tr>
                        <tr>
                            <td><h6>h6. Bootstrap heading</h6></td>
                            <td class="type-info">Semibold 12px</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;h1&gt;</span>h1. Bootstrap heading<span class="nt">&lt;/h1&gt;</span>
<span class="nt">&lt;h2&gt;</span>h2. Bootstrap heading<span class="nt">&lt;/h2&gt;</span>
<span class="nt">&lt;h3&gt;</span>h3. Bootstrap heading<span class="nt">&lt;/h3&gt;</span>
<span class="nt">&lt;h4&gt;</span>h4. Bootstrap heading<span class="nt">&lt;/h4&gt;</span>
<span class="nt">&lt;h5&gt;</span>h5. Bootstrap heading<span class="nt">&lt;/h5&gt;</span>
<span class="nt">&lt;h6&gt;</span>h6. Bootstrap heading<span class="nt">&lt;/h6&gt;</span>
</code></pre></div>

                <p>在标题内还可以包含 <code>&lt;small&gt;</code> 标签或赋予 <code>.small</code> 类的元素，可以用来标记副标题。</p>
                <div class="bs-example bs-example-type">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td><h1>h1. Bootstrap heading <small>Secondary text</small></h1></td>
                        </tr>
                        <tr>
                            <td><h2>h2. Bootstrap heading <small>Secondary text</small></h2></td>
                        </tr>
                        <tr>
                            <td><h3>h3. Bootstrap heading <small>Secondary text</small></h3></td>
                        </tr>
                        <tr>
                            <td><h4>h4. Bootstrap heading <small>Secondary text</small></h4></td>
                        </tr>
                        <tr>
                            <td><h5>h5. Bootstrap heading <small>Secondary text</small></h5></td>
                        </tr>
                        <tr>
                            <td><h6>h6. Bootstrap heading <small>Secondary text</small></h6></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;h1&gt;</span>h1. Bootstrap heading <span class="nt">&lt;small&gt;</span>Secondary text<span class="nt">&lt;/small&gt;&lt;/h1&gt;</span>
<span class="nt">&lt;h2&gt;</span>h2. Bootstrap heading <span class="nt">&lt;small&gt;</span>Secondary text<span class="nt">&lt;/small&gt;&lt;/h2&gt;</span>
<span class="nt">&lt;h3&gt;</span>h3. Bootstrap heading <span class="nt">&lt;small&gt;</span>Secondary text<span class="nt">&lt;/small&gt;&lt;/h3&gt;</span>
<span class="nt">&lt;h4&gt;</span>h4. Bootstrap heading <span class="nt">&lt;small&gt;</span>Secondary text<span class="nt">&lt;/small&gt;&lt;/h4&gt;</span>
<span class="nt">&lt;h5&gt;</span>h5. Bootstrap heading <span class="nt">&lt;small&gt;</span>Secondary text<span class="nt">&lt;/small&gt;&lt;/h5&gt;</span>
<span class="nt">&lt;h6&gt;</span>h6. Bootstrap heading <span class="nt">&lt;small&gt;</span>Secondary text<span class="nt">&lt;/small&gt;&lt;/h6&gt;</span>
</code></pre></div>


                <!-- Body copy -->
                <h2 id="type-body-copy">页面主体</h2>
                <p>Bootstrap 将全局 <code>font-size</code> 设置为 <strong>14px</strong>，<code>line-height</code> 设置为 <strong>1.428</strong>。这些属性直接赋予 <code>&lt;body&gt;</code> 元素和所有段落元素。另外，<code>&lt;p&gt;</code> （段落）元素还被设置了等于 1/2 行高（即 10px）的底部外边距（margin）。</p>
                <div class="bs-example">
                    <p>Nullam quis risus eget urna mollis ornare vel eu leo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh ultricies vehicula.</p>
                    <p>Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec ullamcorper nulla non metus auctor fringilla. Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Donec ullamcorper nulla non metus auctor fringilla.</p>
                    <p>Maecenas sed diam eget risus varius blandit sit amet non magna. Donec id elit non mi porta gravida at eget metus. Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;p&gt;</span>...<span class="nt">&lt;/p&gt;</span>
</code></pre></div>

                <!-- Body copy .lead -->
                <h3>中心内容</h3>
                <p>通过添加 <code>.lead</code> 类可以让段落突出显示。</p>
                <div class="bs-example">
                    <p class="lead">Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Duis mollis, est non commodo luctus.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"lead"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
</code></pre></div>

                <!-- Using Less -->
                <h3>使用 Less 工具构建</h3>
                <p><strong>variables.less</strong> 文件中定义的两个 Less 变量决定了排版尺寸：<code>@font-size-base</code> 和 <code>@line-height-base</code>。第一个变量定义了全局 font-size 基准，第二个变量是 line-height 基准。我们使用这些变量和一些简单的公式计算出其它所有页面元素的 margin、 padding 和 line-height。自定义这些变量即可改变 Bootstrap 的默认样式。</p>

                <!-- Inline text elements -->
                <h2 id="type-inline-text">内联文本元素</h2>
                <h3>Marked text</h3>
                <p>For highlighting a run of text due to its relevance in another context, use the <code>&lt;mark&gt;</code> tag.</p>
                <div class="bs-example">
                    <p>You can use the mark tag to <mark>highlight</mark> text.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html">You can use the mark tag to <span class="nt">&lt;mark&gt;</span>highlight<span class="nt">&lt;/mark&gt;</span> text.
</code></pre></div>


                <h3>被删除的文本</h3>
                <p>对于被删除的文本使用 <code>&lt;del&gt;</code> 标签。</p>
                <div class="bs-example">
                    <p><del>This line of text is meant to be treated as deleted text.</del></p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;del&gt;</span>This line of text is meant to be treated as deleted text.<span class="nt">&lt;/del&gt;</span>
</code></pre></div>

                <h3>无用文本</h3>
                <p>对于没用的文本使用 <code>&lt;s&gt;</code> 标签。</p>
                <div class="bs-example">
                    <p><s>This line of text is meant to be treated as no longer accurate.</s></p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;s&gt;</span>This line of text is meant to be treated as no longer accurate.<span class="nt">&lt;/s&gt;</span>
</code></pre></div>

                <h3>插入文本</h3>
                <p>额外插入的文本使用 <code>&lt;ins&gt;</code> 标签。</p>
                <div class="bs-example">
                    <p><ins>This line of text is meant to be treated as an addition to the document.</ins></p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;ins&gt;</span>This line of text is meant to be treated as an addition to the document.<span class="nt">&lt;/ins&gt;</span>
</code></pre></div>

                <h3>带下划线的文本</h3>
                <p>为文本添加下划线，使用 <code>&lt;u&gt;</code> 标签。</p>
                <div class="bs-example">
                    <p><u>This line of text will render as underlined</u></p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;u&gt;</span>This line of text will render as underlined<span class="nt">&lt;/u&gt;</span>
</code></pre></div>

                <p>利用 HTML 自带的表示强调意味的标签来为文本增添少量样式。</p>

                <h3>小号文本</h3>
                <p>对于不需要强调的inline或block类型的文本，使用 <code>&lt;small&gt;</code> 标签包裹，其内的文本将被设置为父容器字体大小的 85%。标题元素中嵌套的 <code>&lt;small&gt;</code> 元素被设置不同的 <code>font-size</code> 。</p>
                <p>你还可以为行内元素赋予 <code>.small</code> 类以代替任何 <code>&lt;small&gt;</code> 元素。</p>
                <div class="bs-example">
                    <p><small>This line of text is meant to be treated as fine print.</small></p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;small&gt;</span>This line of text is meant to be treated as fine print.<span class="nt">&lt;/small&gt;</span>
</code></pre></div>


                <h3>着重</h3>
                <p>通过增加 font-weight 值强调一段文本。</p>
                <div class="bs-example">
                    <p>The following snippet of text is <strong>rendered as bold text</strong>.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;strong&gt;</span>rendered as bold text<span class="nt">&lt;/strong&gt;</span>
</code></pre></div>

                <h3>斜体</h3>
                <p>用斜体强调一段文本。</p>
                <div class="bs-example">
                    <p>The following snippet of text is <em>rendered as italicized text</em>.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;em&gt;</span>rendered as italicized text<span class="nt">&lt;/em&gt;</span>
</code></pre></div>

                <div class="bs-callout bs-callout-info">
                    <h4>Alternate elements</h4>
                    <p>在 HTML5 中可以放心使用 <code>&lt;b&gt;</code> 和 <code>&lt;i&gt;</code> 标签。<code>&lt;b&gt;</code> 用于高亮单词或短语，不带有任何着重的意味；而 <code>&lt;i&gt;</code> 标签主要用于发言、技术词汇等。</p>
                </div>

                <h2 id="type-alignment">对齐</h2>
                <p>通过文本对齐类，可以简单方便的将文字重新对齐。</p>
                <div class="bs-example">
                    <p class="text-left">Left aligned text.</p>
                    <p class="text-center">Center aligned text.</p>
                    <p class="text-right">Right aligned text.</p>
                    <p class="text-justify">Justified text.</p>
                    <p class="text-nowrap">No wrap text.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-left"</span><span class="nt">&gt;</span>Left aligned text.<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-center"</span><span class="nt">&gt;</span>Center aligned text.<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-right"</span><span class="nt">&gt;</span>Right aligned text.<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-justify"</span><span class="nt">&gt;</span>Justified text.<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-nowrap"</span><span class="nt">&gt;</span>No wrap text.<span class="nt">&lt;/p&gt;</span>
</code></pre></div>

                <h2 id="type-transformation">改变大小写</h2>
                <p>通过这几个类可以改变文本的大小写。</p>
                <div class="bs-example">
                    <p class="text-lowercase">Lowercased text.</p>
                    <p class="text-uppercase">Uppercased text.</p>
                    <p class="text-capitalize">Capitalized text.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-lowercase"</span><span class="nt">&gt;</span>Lowercased text.<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-uppercase"</span><span class="nt">&gt;</span>Uppercased text.<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-capitalize"</span><span class="nt">&gt;</span>Capitalized text.<span class="nt">&lt;/p&gt;</span>
</code></pre></div>

                <!-- Abbreviations -->
                <h2 id="type-abbreviations">缩略语</h2>
                <p>当鼠标悬停在缩写和缩写词上时就会显示完整内容，Bootstrap 实现了对 HTML 的 <code>&lt;abbr&gt;</code> 元素的增强样式。缩略语元素带有 <code>title</code> 属性，外观表现为带有较浅的虚线框，鼠标移至上面时会变成带有“问号”的指针。如想看完整的内容可把鼠标悬停在缩略语上, 但需要包含 title 属性。</p>

                <h3>基本缩略语</h3>
                <p>如想看完整的内容可把鼠标悬停在缩略语上, 但需要为 <code>&lt;abbr&gt;</code> 元素设置 <code>title</code>属性。</p>
                <div class="bs-example">
                    <p>An abbreviation of the word attribute is <abbr title="attribute">attr</abbr>.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;abbr</span> <span class="na">title=</span><span class="s">"attribute"</span><span class="nt">&gt;</span>attr<span class="nt">&lt;/abbr&gt;</span>
</code></pre></div>

                <h3>首字母缩略语</h3>
                <p>为缩略语添加 <code>.initialism</code> 类，可以让 font-size 变得稍微小些。</p>
                <div class="bs-example">
                    <p><abbr title="HyperText Markup Language" class="initialism">HTML</abbr> is the best thing since sliced bread.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;abbr</span> <span class="na">title=</span><span class="s">"HyperText Markup Language"</span> <span class="na">class=</span><span class="s">"initialism"</span><span class="nt">&gt;</span>HTML<span class="nt">&lt;/abbr&gt;</span>
</code></pre></div>


                <!-- Addresses -->
                <h2 id="type-addresses">地址</h2>
                <p>让联系信息以最接近日常使用的格式呈现。在每行结尾添加 <code>&lt;br&gt;</code> 可以保留需要的样式。</p>
                <div class="bs-example">
                    <address>
                        <strong>Twitter, Inc.</strong><br>
                        795 Folsom Ave, Suite 600<br>
                        San Francisco, CA 94107<br>
                        <abbr title="Phone">P:</abbr> (123) 456-7890
                    </address>
                    <address>
                        <strong>Full Name</strong><br>
                        <a href="mailto:#">first.last@example.com</a>
                    </address>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;address&gt;</span>
  <span class="nt">&lt;strong&gt;</span>Twitter, Inc.<span class="nt">&lt;/strong&gt;&lt;br&gt;</span>
  795 Folsom Ave, Suite 600<span class="nt">&lt;br&gt;</span>
  San Francisco, CA 94107<span class="nt">&lt;br&gt;</span>
  <span class="nt">&lt;abbr</span> <span class="na">title=</span><span class="s">"Phone"</span><span class="nt">&gt;</span>P:<span class="nt">&lt;/abbr&gt;</span> (123) 456-7890
<span class="nt">&lt;/address&gt;</span>

<span class="nt">&lt;address&gt;</span>
  <span class="nt">&lt;strong&gt;</span>Full Name<span class="nt">&lt;/strong&gt;&lt;br&gt;</span>
  <span class="nt">&lt;a</span> <span class="na">href=</span><span class="s">"mailto:#"</span><span class="nt">&gt;</span>first.last@example.com<span class="nt">&lt;/a&gt;</span>
<span class="nt">&lt;/address&gt;</span>
</code></pre></div>


                <!-- Blockquotes -->
                <h2 id="type-blockquotes">引用</h2>
                <p>在你的文档中引用其他来源的内容。</p>

                <h3>默认样式的引用</h3>
                <p>将任何 <abbr title="HyperText Markup Language">HTML</abbr> 元素包裹在 <code>&lt;blockquote&gt;</code> 中即可表现为引用样式。对于直接引用，我们建议用 <code>&lt;p&gt;</code> 标签。</p>
                <div class="bs-example">
                    <blockquote>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
                    </blockquote>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;blockquote&gt;</span>
  <span class="nt">&lt;p&gt;</span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;/blockquote&gt;</span>
</code></pre></div>

                <h3>Blockquote options</h3>
                <p>Style and content changes for simple variations on a standard <code>&lt;blockquote&gt;</code>.</p>

                <h4>Naming a source</h4>
                <p>Add a <code>&lt;footer&gt;</code> for identifying the source. Wrap the name of the source work in <code>&lt;cite&gt;</code>.</p>
                <div class="bs-example">
                    <blockquote>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
                        <footer>Someone famous in <cite title="Source Title">Source Title</cite></footer>
                    </blockquote>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;blockquote&gt;</span>
  <span class="nt">&lt;p&gt;</span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.<span class="nt">&lt;/p&gt;</span>
  <span class="nt">&lt;footer&gt;</span>Someone famous in <span class="nt">&lt;cite</span> <span class="na">title=</span><span class="s">"Source Title"</span><span class="nt">&gt;</span>Source Title<span class="nt">&lt;/cite&gt;&lt;/footer&gt;</span>
<span class="nt">&lt;/blockquote&gt;</span>
</code></pre></div>

                <h4>Alternate displays</h4>
                <p>Add <code>.blockquote-reverse</code> for a blockquote with right-aligned content.</p>
                <div class="bs-example" style="overflow: hidden;">
                    <blockquote class="blockquote-reverse">
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
                        <footer>Someone famous in <cite title="Source Title">Source Title</cite></footer>
                    </blockquote>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;blockquote</span> <span class="na">class=</span><span class="s">"blockquote-reverse"</span><span class="nt">&gt;</span>
  ...
<span class="nt">&lt;/blockquote&gt;</span>
</code></pre></div>


                <!-- Lists -->
                <h2 id="type-lists">列表</h2>

                <h3>无序列表</h3>
                <p>排列顺序<em>无关紧要</em>的一列元素。</p>
                <div class="bs-example">
                    <ul>
                        <li>Lorem ipsum dolor sit amet</li>
                        <li>Consectetur adipiscing elit</li>
                        <li>Integer molestie lorem at massa</li>
                        <li>Facilisis in pretium nisl aliquet</li>
                        <li>Nulla volutpat aliquam velit
                            <ul>
                                <li>Phasellus iaculis neque</li>
                                <li>Purus sodales ultricies</li>
                                <li>Vestibulum laoreet porttitor sem</li>
                                <li>Ac tristique libero volutpat at</li>
                            </ul>
                        </li>
                        <li>Faucibus porta lacus fringilla vel</li>
                        <li>Aenean sit amet erat nunc</li>
                        <li>Eget porttitor lorem</li>
                    </ul>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;ul&gt;</span>
  <span class="nt">&lt;li&gt;</span>...<span class="nt">&lt;/li&gt;</span>
<span class="nt">&lt;/ul&gt;</span>
</code></pre></div>

                <h3>有序列表</h3>
                <p>顺序<em>至关重要</em>的一组元素。</p>
                <div class="bs-example">
                    <ol>
                        <li>Lorem ipsum dolor sit amet</li>
                        <li>Consectetur adipiscing elit</li>
                        <li>Integer molestie lorem at massa</li>
                        <li>Facilisis in pretium nisl aliquet</li>
                        <li>Nulla volutpat aliquam velit</li>
                        <li>Faucibus porta lacus fringilla vel</li>
                        <li>Aenean sit amet erat nunc</li>
                        <li>Eget porttitor lorem</li>
                    </ol>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;ol&gt;</span>
  <span class="nt">&lt;li&gt;</span>...<span class="nt">&lt;/li&gt;</span>
<span class="nt">&lt;/ol&gt;</span>
</code></pre></div>

                <h3>无样式列表</h3>
                <p>移除了默认的 <code>list-style</code> 样式和左侧外边距的一组元素（只针对直接子元素）。<strong>这是针对直接子元素的</strong>，也就是说，你需要对所有嵌套的列表都添加这个类才能具有同样的样式。</p>
                <div class="bs-example">
                    <ul class="list-unstyled">
                        <li>Lorem ipsum dolor sit amet</li>
                        <li>Consectetur adipiscing elit</li>
                        <li>Integer molestie lorem at massa</li>
                        <li>Facilisis in pretium nisl aliquet</li>
                        <li>Nulla volutpat aliquam velit
                            <ul>
                                <li>Phasellus iaculis neque</li>
                                <li>Purus sodales ultricies</li>
                                <li>Vestibulum laoreet porttitor sem</li>
                                <li>Ac tristique libero volutpat at</li>
                            </ul>
                        </li>
                        <li>Faucibus porta lacus fringilla vel</li>
                        <li>Aenean sit amet erat nunc</li>
                        <li>Eget porttitor lorem</li>
                    </ul>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;ul</span> <span class="na">class=</span><span class="s">"list-unstyled"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;li&gt;</span>...<span class="nt">&lt;/li&gt;</span>
<span class="nt">&lt;/ul&gt;</span>
</code></pre></div>

                <h3>内联列表</h3>
                <p>通过设置 <code>display: inline-block;</code> 并添加少量的内补（padding），将所有元素放置于同一行。</p>
                <div class="bs-example">
                    <ul class="list-inline">
                        <li>Lorem ipsum</li>
                        <li>Phasellus iaculis</li>
                        <li>Nulla volutpat</li>
                    </ul>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;ul</span> <span class="na">class=</span><span class="s">"list-inline"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;li&gt;</span>...<span class="nt">&lt;/li&gt;</span>
<span class="nt">&lt;/ul&gt;</span>
</code></pre></div>

                <h3>描述</h3>
                <p>带有描述的短语列表。</p>
                <div class="bs-example">
                    <dl>
                        <dt>Description lists</dt>
                        <dd>A description list is perfect for defining terms.</dd>
                        <dt>Euismod</dt>
                        <dd>Vestibulum id ligula porta felis euismod semper eget lacinia odio sem nec elit.</dd>
                        <dd>Donec id elit non mi porta gravida at eget metus.</dd>
                        <dt>Malesuada porta</dt>
                        <dd>Etiam porta sem malesuada magna mollis euismod.</dd>
                    </dl>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;dl&gt;</span>
  <span class="nt">&lt;dt&gt;</span>...<span class="nt">&lt;/dt&gt;</span>
  <span class="nt">&lt;dd&gt;</span>...<span class="nt">&lt;/dd&gt;</span>
<span class="nt">&lt;/dl&gt;</span>
</code></pre></div>

                <h4>水平排列的描述</h4>
                <p><code>.dl-horizontal</code> 可以让 <code>&lt;dl&gt;</code> 内的短语及其描述排在一行。开始是像 <code>&lt;dl&gt;</code> 的默认样式堆叠在一起，随着导航条逐渐展开而排列在一行。</p>
                <div class="bs-example">
                    <dl class="dl-horizontal">
                        <dt>Description lists</dt>
                        <dd>A description list is perfect for defining terms.</dd>
                        <dt>Euismod</dt>
                        <dd>Vestibulum id ligula porta felis euismod semper eget lacinia odio sem nec elit.</dd>
                        <dd>Donec id elit non mi porta gravida at eget metus.</dd>
                        <dt>Malesuada porta</dt>
                        <dd>Etiam porta sem malesuada magna mollis euismod.</dd>
                        <dt>Felis euismod semper eget lacinia</dt>
                        <dd>Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</dd>
                    </dl>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;dl</span> <span class="na">class=</span><span class="s">"dl-horizontal"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;dt&gt;</span>...<span class="nt">&lt;/dt&gt;</span>
  <span class="nt">&lt;dd&gt;</span>...<span class="nt">&lt;/dd&gt;</span>
<span class="nt">&lt;/dl&gt;</span>
</code></pre></div>

                <div class="bs-callout bs-callout-info">
                    <h4>自动截断</h4>
                    <p>通过 <code>text-overflow</code> 属性，水平排列的描述列表将会截断左侧太长的短语。在较窄的视口（viewport）内，列表将变为默认堆叠排列的布局方式。</p>
                </div>
            </div>

            <div class="bs-docs-section">
                <h1 id="code" class="page-header">代码</h1>

                <h2 id="code-inline">内联代码</h2>
                <p>通过 <code>&lt;code&gt;</code> 标签包裹内联样式的代码片段。</p>
                <div class="bs-example">
                    For example, <code>&lt;section&gt;</code> should be wrapped as inline.
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html">For example, <span class="nt">&lt;code&gt;</span><span class="ni">&amp;lt;</span>section<span class="ni">&amp;gt;</span><span class="nt">&lt;/code&gt;</span> should be wrapped as inline.
</code></pre></div>

                <h2 id="code-user-input">用户输入</h2>
                <p>通过 <code>&lt;kbd&gt;</code> 标签标记用户通过键盘输入的内容。=</p>
                <div class="bs-example">
                    To switch directories, type <kbd>cd</kbd> followed by the name of the directory.<br>
                    To edit settings, press <kbd><kbd>ctrl</kbd> + <kbd>,</kbd></kbd>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html">To switch directories, type <span class="nt">&lt;kbd&gt;</span>cd<span class="nt">&lt;/kbd&gt;</span> followed by the name of the directory.<span class="nt">&lt;br&gt;</span>
To edit settings, press <span class="nt">&lt;kbd&gt;&lt;kbd&gt;</span>ctrl<span class="nt">&lt;/kbd&gt;</span> + <span class="nt">&lt;kbd&gt;</span>,<span class="nt">&lt;/kbd&gt;&lt;/kbd&gt;</span>
</code></pre></div>

                <h2 id="code-block">代码块</h2>
                <p>多行代码可以使用 <code>&lt;pre&gt;</code> 标签。为了正确的展示代码，注意将尖括号做转义处理。</p>
                <div class="bs-example">
                    <pre>&lt;p&gt;Sample text here...&lt;/p&gt;</pre>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;pre&gt;</span><span class="ni">&amp;lt;</span>p<span class="ni">&amp;gt;</span>Sample text here...<span class="ni">&amp;lt;</span>/p<span class="ni">&amp;gt;</span><span class="nt">&lt;/pre&gt;</span>
</code></pre></div>

                <p>还可以使用 <code>.pre-scrollable</code> 类，其作用是设置 max-height 为 350px ，并在垂直方向展示滚动条。</p>
                <h2 id="code-variables">变量</h2>
                <p>通过 <code>&lt;var&gt;</code> 标签标记变量。</p>
                <div class="bs-example">
                    <p><var>y</var> = <var>m</var><var>x</var> + <var>b</var></p>

                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;var&gt;</span>y<span class="nt">&lt;/var&gt;</span> = <span class="nt">&lt;var&gt;</span>m<span class="nt">&lt;/var&gt;&lt;var&gt;</span>x<span class="nt">&lt;/var&gt;</span> + <span class="nt">&lt;var&gt;</span>b<span class="nt">&lt;/var&gt;</span>
</code></pre></div>

                <h2 id="code-sample-output">程序输出</h2>
                <p>通过 <code>&lt;samp&gt;</code> 标签来标记程序输出的内容。</p>
                <div class="bs-example">
                    <p><samp>This text is meant to be treated as sample output from a computer program.</samp></p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;samp&gt;</span>This text is meant to be treated as sample output from a computer program.<span class="nt">&lt;/samp&gt;</span>
</code></pre></div>
            </div>

            <div class="bs-docs-section">
                <h1 id="tables" class="page-header">表格</h1>

                <h2 id="tables-example">基本实例</h2>
                <p>为任意 <code>&lt;table&gt;</code> 标签添加 <code>.table</code> 类可以为其赋予基本的样式 — 少量的内补（padding）和水平方向的分隔线。这种方式看起来很多余！？但是我们觉得，表格元素使用的很广泛，如果我们为其赋予默认样式可能会影响例如日历和日期选择之类的插件，所以我们选择将此样式独立出来。</p>
                <div class="bs-example">
                    <table class="table">
                        <caption>Optional table caption.</caption>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Username</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>Mark</td>
                            <td>Otto</td>
                            <td>@mdo</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Jacob</td>
                            <td>Thornton</td>
                            <td>@fat</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>Larry</td>
                            <td>the Bird</td>
                            <td>@twitter</td>
                        </tr>
                        </tbody>
                    </table>
                </div><!-- /example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;table</span> <span class="na">class=</span><span class="s">"table"</span><span class="nt">&gt;</span>
  ...
<span class="nt">&lt;/table&gt;</span>
</code></pre></div>


                <h2 id="tables-striped">条纹状表格</h2>
                <p>通过 <code>.table-striped</code> 类可以给 <code>&lt;tbody&gt;</code> 之内的每一行增加斑马条纹样式。</p>
                <div class="bs-callout bs-callout-danger">
                    <h4>跨浏览器兼容性</h4>
                    <p>条纹状表格是依赖 <code>:nth-child</code> CSS 选择器实现的，而这一功能不被 Internet Explorer 8 支持。</p>
                </div>
                <div class="bs-example">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Username</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>Mark</td>
                            <td>Otto</td>
                            <td>@mdo</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Jacob</td>
                            <td>Thornton</td>
                            <td>@fat</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>Larry</td>
                            <td>the Bird</td>
                            <td>@twitter</td>
                        </tr>
                        </tbody>
                    </table>
                </div><!-- /example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;table</span> <span class="na">class=</span><span class="s">"table table-striped"</span><span class="nt">&gt;</span>
  ...
<span class="nt">&lt;/table&gt;</span>
</code></pre></div>


                <h2 id="tables-bordered">带边框的表格</h2>
                <p>添加 <code>.table-bordered</code> 类为表格和其中的每个单元格增加边框。</p>
                <div class="bs-example">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Username</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td rowspan="2">1</td>
                            <td>Mark</td>
                            <td>Otto</td>
                            <td>@mdo</td>
                        </tr>
                        <tr>
                            <td>Mark</td>
                            <td>Otto</td>
                            <td>@TwBootstrap</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Jacob</td>
                            <td>Thornton</td>
                            <td>@fat</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td colspan="2">Larry the Bird</td>
                            <td>@twitter</td>
                        </tr>
                        </tbody>
                    </table>
                </div><!-- /example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;table</span> <span class="na">class=</span><span class="s">"table table-bordered"</span><span class="nt">&gt;</span>
  ...
<span class="nt">&lt;/table&gt;</span>
</code></pre></div>


                <h2 id="tables-hover-rows">鼠标悬停</h2>
                <p>通过添加 <code>.table-hover</code> 类可以让 <code>&lt;tbody&gt;</code> 中的每一行对鼠标悬停状态作出响应。</p>
                <div class="bs-example">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Username</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>Mark</td>
                            <td>Otto</td>
                            <td>@mdo</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Jacob</td>
                            <td>Thornton</td>
                            <td>@fat</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td colspan="2">Larry the Bird</td>
                            <td>@twitter</td>
                        </tr>
                        </tbody>
                    </table>
                </div><!-- /example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;table</span> <span class="na">class=</span><span class="s">"table table-hover"</span><span class="nt">&gt;</span>
  ...
<span class="nt">&lt;/table&gt;</span>
</code></pre></div>


                <h2 id="tables-condensed">紧缩表格</h2>
                <p>通过添加 <code>.table-condensed</code> 类可以让表格更加紧凑，单元格中的内补（padding）均会减半。</p>
                <div class="bs-example">
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Username</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>Mark</td>
                            <td>Otto</td>
                            <td>@mdo</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Jacob</td>
                            <td>Thornton</td>
                            <td>@fat</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td colspan="2">Larry the Bird</td>
                            <td>@twitter</td>
                        </tr>
                        </tbody>
                    </table>
                </div><!-- /example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;table</span> <span class="na">class=</span><span class="s">"table table-condensed"</span><span class="nt">&gt;</span>
  ...
<span class="nt">&lt;/table&gt;</span>
</code></pre></div>


                <h2 id="tables-contextual-classes">状态类</h2>
                <p>通过这些状态类可以为行或单元格设置颜色。</p>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <colgroup>
                            <col class="col-xs-1">
                            <col class="col-xs-7">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>Class</th>
                            <th>描述</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <code>.active</code>
                            </td>
                            <td>鼠标悬停在行或单元格上时所设置的颜色</td>
                        </tr>
                        <tr>
                            <td>
                                <code>.success</code>
                            </td>
                            <td>标识成功或积极的动作</td>
                        </tr>
                        <tr>
                            <td>
                                <code>.info</code>
                            </td>
                            <td>标识普通的提示信息或动作</td>
                        </tr>
                        <tr>
                            <td>
                                <code>.warning</code>
                            </td>
                            <td>标识警告或需要用户注意</td>
                        </tr>
                        <tr>
                            <td>
                                <code>.danger</code>
                            </td>
                            <td>标识危险或潜在的带来负面影响的动作</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="bs-example">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Column heading</th>
                            <th>Column heading</th>
                            <th>Column heading</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="active">
                            <td>1</td>
                            <td>Column content</td>
                            <td>Column content</td>
                            <td>Column content</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Column content</td>
                            <td>Column content</td>
                            <td>Column content</td>
                        </tr>
                        <tr class="success">
                            <td>3</td>
                            <td>Column content</td>
                            <td>Column content</td>
                            <td>Column content</td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td>Column content</td>
                            <td>Column content</td>
                            <td>Column content</td>
                        </tr>
                        <tr class="info">
                            <td>5</td>
                            <td>Column content</td>
                            <td>Column content</td>
                            <td>Column content</td>
                        </tr>
                        <tr>
                            <td>6</td>
                            <td>Column content</td>
                            <td>Column content</td>
                            <td>Column content</td>
                        </tr>
                        <tr class="warning">
                            <td>7</td>
                            <td>Column content</td>
                            <td>Column content</td>
                            <td>Column content</td>
                        </tr>
                        <tr>
                            <td>8</td>
                            <td>Column content</td>
                            <td>Column content</td>
                            <td>Column content</td>
                        </tr>
                        <tr class="danger">
                            <td>9</td>
                            <td>Column content</td>
                            <td>Column content</td>
                            <td>Column content</td>
                        </tr>
                        </tbody>
                    </table>
                </div><!-- /example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="c">&lt;!-- On rows --&gt;</span>
<span class="nt">&lt;tr</span> <span class="na">class=</span><span class="s">"active"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/tr&gt;</span>
<span class="nt">&lt;tr</span> <span class="na">class=</span><span class="s">"success"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/tr&gt;</span>
<span class="nt">&lt;tr</span> <span class="na">class=</span><span class="s">"warning"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/tr&gt;</span>
<span class="nt">&lt;tr</span> <span class="na">class=</span><span class="s">"danger"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/tr&gt;</span>
<span class="nt">&lt;tr</span> <span class="na">class=</span><span class="s">"info"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/tr&gt;</span>

<span class="c">&lt;!-- On cells (`td` or `th`) --&gt;</span>
<span class="nt">&lt;tr&gt;</span>
  <span class="nt">&lt;td</span> <span class="na">class=</span><span class="s">"active"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/td&gt;</span>
  <span class="nt">&lt;td</span> <span class="na">class=</span><span class="s">"success"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/td&gt;</span>
  <span class="nt">&lt;td</span> <span class="na">class=</span><span class="s">"warning"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/td&gt;</span>
  <span class="nt">&lt;td</span> <span class="na">class=</span><span class="s">"danger"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/td&gt;</span>
  <span class="nt">&lt;td</span> <span class="na">class=</span><span class="s">"info"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/td&gt;</span>
<span class="nt">&lt;/tr&gt;</span>
</code></pre></div>


                <h2 id="tables-responsive">响应式表格</h2>
                <p>将任何 <code>.table</code> 元素包裹在 <code>.table-responsive</code> 元素内，即可创建响应式表格，其会在小屏幕设备上（小于768px）水平滚动。当屏幕大于 768px 宽度时，水平滚动条消失。</p>

                <div class="bs-callout bs-callout-warning">
                    <h4>Firefox 和 <code>fieldset</code> 元素</h4>
                    <p>Firefox 浏览器对 <code>fieldset</code> 元素设置了一些影响 <code>width</code> 属性的样式，导致响应式表格出现问题。除非使用我们下面提供的针对 Firefox 的 hack 代码，否则无解：</p>
                    <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="css"><span class="k">@-moz-document</span> <span class="nt">url-prefix</span><span class="o">()</span> <span class="p">{</span>
  <span class="nt">fieldset</span> <span class="p">{</span> <span class="k">display</span><span class="o">:</span> <span class="k">table-cell</span><span class="p">;</span> <span class="p">}</span>
<span class="p">}</span>
</code></pre></div>
                    <p>更多信息请参考 <a href="http://stackoverflow.com/questions/17408815/fieldset-resizes-wrong-appears-to-have-unremovable-min-width-min-content/17863685#17863685">this Stack Overflow answer</a>.</p>
                </div>

                <div class="bs-example">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                            </tr>
                            </tbody>
                        </table>
                    </div><!-- /.table-responsive -->

                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                                <th>Table heading</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                                <td>Table cell</td>
                            </tr>
                            </tbody>
                        </table>
                    </div><!-- /.table-responsive -->
                </div><!-- /example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"table-responsive"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;table</span> <span class="na">class=</span><span class="s">"table"</span><span class="nt">&gt;</span>
    ...
  <span class="nt">&lt;/table&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>
            </div>

            <div class="bs-docs-section">
                <h1 id="forms" class="page-header">表单</h1>

                <h2 id="forms-example">基本实例</h2>
                <p>单独的表单控件会被自动赋予一些全局样式。所有设置了 <code>.form-control</code> 类的 <code>&lt;input&gt;</code>、<code>&lt;textarea&gt;</code> 和 <code>&lt;select&gt;</code> 元素都将被默认设置宽度属性为 <code>width: 100%;</code>。 将 <code>label</code> 元素和前面提到的控件包裹在 <code>.form-group</code> 中可以获得最好的排列。</p>
                <div class="bs-example">
                    <form role="form">
                        <div class="form-group">
                            <label for="exampleInputEmail1">Email address</label>
                            <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label>
                            <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputFile">File input</label>
                            <input type="file" id="exampleInputFile">
                            <p class="help-block">Example block-level help text here.</p>
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox"> Check me out
                            </label>
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div><!-- /example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;form</span> <span class="na">role=</span><span class="s">"form"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">for=</span><span class="s">"exampleInputEmail1"</span><span class="nt">&gt;</span>Email address<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"email"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"exampleInputEmail1"</span> <span class="na">placeholder=</span><span class="s">"Enter email"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">for=</span><span class="s">"exampleInputPassword1"</span><span class="nt">&gt;</span>Password<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"password"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"exampleInputPassword1"</span> <span class="na">placeholder=</span><span class="s">"Password"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">for=</span><span class="s">"exampleInputFile"</span><span class="nt">&gt;</span>File input<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"file"</span> <span class="na">id=</span><span class="s">"exampleInputFile"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"help-block"</span><span class="nt">&gt;</span>Example block-level help text here.<span class="nt">&lt;/p&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span> Check me out
    <span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"submit"</span> <span class="na">class=</span><span class="s">"btn btn-default"</span><span class="nt">&gt;</span>Submit<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;/form&gt;</span>
</code></pre></div>
                <div class="bs-callout bs-callout-warning">
                    <h4>不要将表单组合输入框组混合使用</h4>
                    <p>不要将表单组直接和<a href="/components/#input-groups">输入框组</a>混合使用。建议将输入框组嵌套到表单组中使用。</p>
                </div>


                <h2 id="forms-inline">内联表单</h2>
                <p>为 <code>&lt;form&gt;</code> 元素添加 <code>.form-inline</code> 类可使其内容左对齐并且表现为 <code>inline-block</code> 级别的控件。<strong>只适用于视口（viewport）至少在 768px 宽度时（视口宽度再小的话就会使表单折叠）。</strong></p>
                <div class="bs-callout bs-callout-danger">
                    <h4>需要手动设置宽度</h4>
                    <p>在 Bootstrap 中，输入框和单选/多选框控件默认被设置为 <code>width: 100%;</code> 宽度。在内联表单，我们将这些元素的宽度设置为 <code>width: auto;</code>，因此，多个控件可以排列在同一行。根据你的布局需求，可能需要一些额外的定制化组件。</p>
                </div>
                <div class="bs-callout bs-callout-warning">
                    <h4>一定要添加 <code>label</code> 标签</h4>
                    <p>如果你没有为每个输入控件设置 <code>label</code> 标签，屏幕阅读器将无法正确识别。对于这些内联表单，你可以通过为 <code>label</code> 设置 <code>.sr-only</code> 类将其隐藏。</p>
                </div>
                <div class="bs-example">
                    <form class="form-inline" role="form">
                        <div class="form-group">
                            <label class="sr-only" for="exampleInputEmail2">Email address</label>
                            <input type="email" class="form-control" id="exampleInputEmail2" placeholder="Enter email">
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">@</div>
                                <input class="form-control" type="email" placeholder="Enter email">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="exampleInputPassword2">Password</label>
                            <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Password">
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox"> Remember me
                            </label>
                        </div>
                        <button type="submit" class="btn btn-default">Sign in</button>
                    </form>
                </div><!-- /example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;form</span> <span class="na">class=</span><span class="s">"form-inline"</span> <span class="na">role=</span><span class="s">"form"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"sr-only"</span> <span class="na">for=</span><span class="s">"exampleInputEmail2"</span><span class="nt">&gt;</span>Email address<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"email"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"exampleInputEmail2"</span> <span class="na">placeholder=</span><span class="s">"Enter email"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"input-group"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"input-group-addon"</span><span class="nt">&gt;</span>@<span class="nt">&lt;/div&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">type=</span><span class="s">"email"</span> <span class="na">placeholder=</span><span class="s">"Enter email"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"sr-only"</span> <span class="na">for=</span><span class="s">"exampleInputPassword2"</span><span class="nt">&gt;</span>Password<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"password"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"exampleInputPassword2"</span> <span class="na">placeholder=</span><span class="s">"Password"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span> Remember me
    <span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"submit"</span> <span class="na">class=</span><span class="s">"btn btn-default"</span><span class="nt">&gt;</span>Sign in<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;/form&gt;</span>
</code></pre></div>


                <h2 id="forms-horizontal">水平排列的表单</h2>
                <p>通过为表单添加 <code>.form-horizontal</code> 类，并联合使用 Bootstrap 预置的栅格类，可以将 <code>label</code> 标签和控件组水平并排布局。这样做将改变 <code>.form-group</code> 的行为，使其表现为栅格系统中的行（row），因此就无需再额外添加 <code>.row</code> 了。</p>
                <div class="bs-example">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
                            <div class="col-sm-10">
                                <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox"> Remember me
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default">Sign in</button>
                            </div>
                        </div>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;form</span> <span class="na">class=</span><span class="s">"form-horizontal"</span> <span class="na">role=</span><span class="s">"form"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">for=</span><span class="s">"inputEmail3"</span> <span class="na">class=</span><span class="s">"col-sm-2 control-label"</span><span class="nt">&gt;</span>Email<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-10"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"email"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputEmail3"</span> <span class="na">placeholder=</span><span class="s">"Email"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">for=</span><span class="s">"inputPassword3"</span> <span class="na">class=</span><span class="s">"col-sm-2 control-label"</span><span class="nt">&gt;</span>Password<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-10"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"password"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputPassword3"</span> <span class="na">placeholder=</span><span class="s">"Password"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-offset-2 col-sm-10"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span>
        <span class="nt">&lt;label&gt;</span>
          <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span> Remember me
        <span class="nt">&lt;/label&gt;</span>
      <span class="nt">&lt;/div&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-offset-2 col-sm-10"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"submit"</span> <span class="na">class=</span><span class="s">"btn btn-default"</span><span class="nt">&gt;</span>Sign in<span class="nt">&lt;/button&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/form&gt;</span>
</code></pre></div>


                <h2 id="forms-controls">被支持的控件</h2>
                <p>表单布局实例中展示了其所支持的标准表单控件。</p>

                <h3>输入框</h3>
                <p>包括大部分表单控件、文本输入域控件，还支持所有 HTML5 类型的输入控件： <code>text</code>、<code>password</code>、<code>datetime</code>、<code>datetime-local</code>、<code>date</code>、<code>month</code>、<code>time</code>、<code>week</code>、<code>number</code>、<code>email</code>、<code>url</code>、<code>search</code>、<code>tel</code> 和 <code>color</code>。</p>
                <div class="bs-callout bs-callout-danger">
                    <h4>必须添加类型声明</h4>
                    <p>只有正确设置了 <code>type</code> 属性的输入控件才能被赋予正确的样式。</p>
                </div>
                <div class="bs-example">
                    <form role="form">
                        <input type="text" class="form-control" placeholder="Text input">
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">placeholder=</span><span class="s">"Text input"</span><span class="nt">&gt;</span>
</code></pre></div>
                <div class="bs-callout bs-callout-info">
                    <h4>输入控件组</h4>
                    <p>如需在文本输入域 <code>&lt;input&gt;</code> 前面或后面添加文本内容或按钮控件，请参考<a href="../components/#input-groups">输入控件组</a>。</p>
                </div>

                <h3>文本域</h3>
                <p>支持多行文本的表单控件。可根据需要改变 <code>rows</code> 属性。</p>
                <div class="bs-example">
                    <form role="form">
                        <textarea class="form-control" rows="3"></textarea>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;textarea</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">rows=</span><span class="s">"3"</span><span class="nt">&gt;&lt;/textarea&gt;</span>
</code></pre></div>

                <h3>多选和单选框</h3>
                <p>多选框（checkbox）用于选择列表中的一个或多个选项，而单选框（radio）用于从多个选项中只选择一个。</p>
                <p>设置了 <code>disabled</code> 属性的单选或多选框都能被赋予合适的样式。对于和多选或单选框联合使用的 <code>&lt;label&gt;</code> 标签，如果也希望将悬停于上方的鼠标设置为“禁止点击”的样式，请将 <code>.disabled</code> 类赋予 <code>.radio</code>、<code>.radio-inline</code>、<code>.checkbox</code>、<code>.checkbox-inline</code> 或 <code>&lt;fieldset&gt;</code>。</p>
                <h4>默认外观（堆叠在一起）</h4>
                <div class="bs-example">
                    <form role="form">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" value="">
                                Option one is this and that—be sure to include why it's great
                            </label>
                        </div>
                        <div class="checkbox disabled">
                            <label>
                                <input type="checkbox" value="" disabled="">
                                Option two is disabled
                            </label>
                        </div>
                        <br>
                        <div class="radio">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked="">
                                Option one is this and that—be sure to include why it's great
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                                Option two can be something else and selecting it will deselect option one
                            </label>
                        </div>
                        <div class="radio disabled">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" disabled="">
                                Option three is disabled
                            </label>
                        </div>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span> <span class="na">value=</span><span class="s">""</span><span class="nt">&gt;</span>
    Option one is this and that<span class="ni">&amp;mdash;</span>be sure to include why it's great
  <span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox disabled"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span> <span class="na">value=</span><span class="s">""</span> <span class="na">disabled</span><span class="nt">&gt;</span>
    Option two is disabled
  <span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;/div&gt;</span>

<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"radio"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"radio"</span> <span class="na">name=</span><span class="s">"optionsRadios"</span> <span class="na">id=</span><span class="s">"optionsRadios1"</span> <span class="na">value=</span><span class="s">"option1"</span> <span class="na">checked</span><span class="nt">&gt;</span>
    Option one is this and that<span class="ni">&amp;mdash;</span>be sure to include why it's great
  <span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"radio"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"radio"</span> <span class="na">name=</span><span class="s">"optionsRadios"</span> <span class="na">id=</span><span class="s">"optionsRadios2"</span> <span class="na">value=</span><span class="s">"option2"</span><span class="nt">&gt;</span>
    Option two can be something else and selecting it will deselect option one
  <span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"radio disabled"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"radio"</span> <span class="na">name=</span><span class="s">"optionsRadios"</span> <span class="na">id=</span><span class="s">"optionsRadios3"</span> <span class="na">value=</span><span class="s">"option3"</span> <span class="na">disabled</span><span class="nt">&gt;</span>
    Option three is disabled
  <span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h4>内联单选和多选框</h4>
                <p>通过将 <code>.checkbox-inline</code> 或 <code>.radio-inline</code> 类应用到一系列的多选框（checkbox）或单选框（radio）控件上，可以使这些控件排列在一行。</p>
                <div class="bs-example">
                    <form role="form">
                        <label class="checkbox-inline">
                            <input type="checkbox" id="inlineCheckbox1" value="option1"> 1
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" id="inlineCheckbox2" value="option2"> 2
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" id="inlineCheckbox3" value="option3"> 3
                        </label>
                    </form>
                    <br>
                    <form role="form">
                        <label class="radio-inline">
                            <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"> 1
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2"> 2
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3"> 3
                        </label>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"checkbox-inline"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span> <span class="na">id=</span><span class="s">"inlineCheckbox1"</span> <span class="na">value=</span><span class="s">"option1"</span><span class="nt">&gt;</span> 1
<span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"checkbox-inline"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span> <span class="na">id=</span><span class="s">"inlineCheckbox2"</span> <span class="na">value=</span><span class="s">"option2"</span><span class="nt">&gt;</span> 2
<span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"checkbox-inline"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span> <span class="na">id=</span><span class="s">"inlineCheckbox3"</span> <span class="na">value=</span><span class="s">"option3"</span><span class="nt">&gt;</span> 3
<span class="nt">&lt;/label&gt;</span>

<span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"radio-inline"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"radio"</span> <span class="na">name=</span><span class="s">"inlineRadioOptions"</span> <span class="na">id=</span><span class="s">"inlineRadio1"</span> <span class="na">value=</span><span class="s">"option1"</span><span class="nt">&gt;</span> 1
<span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"radio-inline"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"radio"</span> <span class="na">name=</span><span class="s">"inlineRadioOptions"</span> <span class="na">id=</span><span class="s">"inlineRadio2"</span> <span class="na">value=</span><span class="s">"option2"</span><span class="nt">&gt;</span> 2
<span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"radio-inline"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"radio"</span> <span class="na">name=</span><span class="s">"inlineRadioOptions"</span> <span class="na">id=</span><span class="s">"inlineRadio3"</span> <span class="na">value=</span><span class="s">"option3"</span><span class="nt">&gt;</span> 3
<span class="nt">&lt;/label&gt;</span>
</code></pre></div>

                <h4>Checkboxes and radios without labels</h4>
                <p>Should you have no text within the <code>&lt;label&gt;</code>, the input is positioned as you'd expect. <strong>Currently only works on non-inline checkboxes and radios.</strong></p>
                <div class="bs-example">
                    <form role="form">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" id="blankCheckbox" value="option1">
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="blankRadio" id="blankRadio1" value="option1">
                            </label>
                        </div>
                    </form>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span> <span class="na">id=</span><span class="s">"blankCheckbox"</span> <span class="na">value=</span><span class="s">"option1"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"radio"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"radio"</span> <span class="na">name=</span><span class="s">"blankRadio"</span> <span class="na">id=</span><span class="s">"blankRadio1"</span> <span class="na">value=</span><span class="s">"option1"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/label&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h3>下拉列表（select）</h3>
                <p>使用默认选项或添加 <code>multiple</code> 属性可以同时显示多个选项。</p>
                <div class="bs-example">
                    <form role="form">
                        <select class="form-control">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                        <br>
                        <select multiple="" class="form-control">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;select</span> <span class="na">class=</span><span class="s">"form-control"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;option&gt;</span>1<span class="nt">&lt;/option&gt;</span>
  <span class="nt">&lt;option&gt;</span>2<span class="nt">&lt;/option&gt;</span>
  <span class="nt">&lt;option&gt;</span>3<span class="nt">&lt;/option&gt;</span>
  <span class="nt">&lt;option&gt;</span>4<span class="nt">&lt;/option&gt;</span>
  <span class="nt">&lt;option&gt;</span>5<span class="nt">&lt;/option&gt;</span>
<span class="nt">&lt;/select&gt;</span>

<span class="nt">&lt;select</span> <span class="na">multiple</span> <span class="na">class=</span><span class="s">"form-control"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;option&gt;</span>1<span class="nt">&lt;/option&gt;</span>
  <span class="nt">&lt;option&gt;</span>2<span class="nt">&lt;/option&gt;</span>
  <span class="nt">&lt;option&gt;</span>3<span class="nt">&lt;/option&gt;</span>
  <span class="nt">&lt;option&gt;</span>4<span class="nt">&lt;/option&gt;</span>
  <span class="nt">&lt;option&gt;</span>5<span class="nt">&lt;/option&gt;</span>
<span class="nt">&lt;/select&gt;</span>
</code></pre></div>


                <h2 id="forms-controls-static">静态控件</h2>
                <p>如果需要在表单中将一行纯文本和 <code>label</code> 元素放置于同一行，为 <code>&lt;p&gt;</code> 元素添加 <code>.form-control-static</code> 类即可。</p>
                <div class="bs-example">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Email</label>
                            <div class="col-sm-10">
                                <p class="form-control-static">email@example.com</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword" class="col-sm-2 control-label">Password</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="inputPassword" placeholder="Password">
                            </div>
                        </div>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;form</span> <span class="na">class=</span><span class="s">"form-horizontal"</span> <span class="na">role=</span><span class="s">"form"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"col-sm-2 control-label"</span><span class="nt">&gt;</span>Email<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-10"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"form-control-static"</span><span class="nt">&gt;</span>email@example.com<span class="nt">&lt;/p&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">for=</span><span class="s">"inputPassword"</span> <span class="na">class=</span><span class="s">"col-sm-2 control-label"</span><span class="nt">&gt;</span>Password<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-10"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"password"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputPassword"</span> <span class="na">placeholder=</span><span class="s">"Password"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/form&gt;</span>
</code></pre></div>
                <div class="bs-example">
                    <form class="form-inline" role="form">
                        <div class="form-group">
                            <label class="sr-only">Email</label>
                            <p class="form-control-static">email@example.com</p>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword2" class="sr-only">Password</label>
                            <input type="password" class="form-control" id="inputPassword2" placeholder="Password">
                        </div>
                        <button type="submit" class="btn btn-default">Confirm identity</button>
                    </form>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;form</span> <span class="na">class=</span><span class="s">"form-inline"</span> <span class="na">role=</span><span class="s">"form"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"sr-only"</span><span class="nt">&gt;</span>Email<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"form-control-static"</span><span class="nt">&gt;</span>email@example.com<span class="nt">&lt;/p&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">for=</span><span class="s">"inputPassword2"</span> <span class="na">class=</span><span class="s">"sr-only"</span><span class="nt">&gt;</span>Password<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"password"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputPassword2"</span> <span class="na">placeholder=</span><span class="s">"Password"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"submit"</span> <span class="na">class=</span><span class="s">"btn btn-default"</span><span class="nt">&gt;</span>Confirm identity<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;/form&gt;</span>
</code></pre></div>

                <h2 id="forms-control-focus">输入框焦点</h2>
                <p>我们将某些表单控件的默认 <code>outline</code> 样式移除，然后对 <code>:focus</code> 状态赋予 <code>box-shadow</code> 属性。</p>
                <div class="bs-example">
                    <form role="form">
                        <input class="form-control" id="focusedInput" type="text" value="Demonstrative focus state">
                    </form>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>演示<code>:focus</code> 状态</h4>
                    <p>在本文档中，我们为上面实例中的输入框赋予了自定义的样式，用于演示 <code>.form-control</code> 元素的 <code>:focus</code> 状态。</p>
                </div>


                <h2 id="forms-control-disabled">被禁用的输入框</h2>
                <p>为输入框设置 <code>disabled</code> 属性可以防止用户输入，并能对外观做一些修改，使其更直观。</p>
                <div class="bs-example">
                    <form role="form">
                        <input class="form-control" id="disabledInput" type="text" placeholder="Disabled input here…" disabled="">
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"disabledInput"</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">placeholder=</span><span class="s">"Disabled input here..."</span> <span class="na">disabled</span><span class="nt">&gt;</span>
</code></pre></div>

                <h3 id="forms-disabled-fieldsets">被禁用的 <code>fieldset</code></h3>
                <p>为<code>&lt;fieldset&gt;</code> 设置 <code>disabled</code> 属性,可以禁用 <code>&lt;fieldset&gt;</code> 中包含的所有控件。</p>

                <div class="bs-callout bs-callout-warning">
                    <h4><code>&lt;a&gt;</code> 标签的链接功能不受影响</h4>
                    <p>我们试图通过设置 <code>pointer-events: none</code> 来禁用 <code>&lt;a class="btn btn-*"&gt;</code> 按钮的链接功能，但是这个 CSS 属性尚未标准化，目前也没有被所有浏览器支持，包括 Opera 18 或 Internet Explorer 11 及更低版本。建议用户自己通过 JavaScript 代码禁用链接功能。</p>
                </div>

                <div class="bs-callout bs-callout-danger">
                    <h4>跨浏览器兼容性</h4>
                    <p>虽然 Bootstrap 会将这些样式应用到所有浏览器上，Internet Explorer 11 及以下浏览器中的 <code>&lt;fieldset&gt;</code> 元素并不完全支持 <code>disabled</code> 属性。因此建议在这些浏览器上通过 JavaScript 代码来禁用 <code>&lt;fieldset&gt;</code>。</p>
                </div>

                <div class="bs-example">
                    <form role="form">
                        <fieldset disabled="">
                            <div class="form-group">
                                <label for="disabledTextInput">Disabled input</label>
                                <input type="text" id="disabledTextInput" class="form-control" placeholder="Disabled input">
                            </div>
                            <div class="form-group">
                                <label for="disabledSelect">Disabled select menu</label>
                                <select id="disabledSelect" class="form-control">
                                    <option>Disabled select</option>
                                </select>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Can't check this
                                </label>
                            </div>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </fieldset>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;form</span> <span class="na">role=</span><span class="s">"form"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;fieldset</span> <span class="na">disabled</span><span class="nt">&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;label</span> <span class="na">for=</span><span class="s">"disabledTextInput"</span><span class="nt">&gt;</span>Disabled input<span class="nt">&lt;/label&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">id=</span><span class="s">"disabledTextInput"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">placeholder=</span><span class="s">"Disabled input"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;label</span> <span class="na">for=</span><span class="s">"disabledSelect"</span><span class="nt">&gt;</span>Disabled select menu<span class="nt">&lt;/label&gt;</span>
      <span class="nt">&lt;select</span> <span class="na">id=</span><span class="s">"disabledSelect"</span> <span class="na">class=</span><span class="s">"form-control"</span><span class="nt">&gt;</span>
        <span class="nt">&lt;option&gt;</span>Disabled select<span class="nt">&lt;/option&gt;</span>
      <span class="nt">&lt;/select&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;label&gt;</span>
        <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span> Can't check this
      <span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
    <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"submit"</span> <span class="na">class=</span><span class="s">"btn btn-primary"</span><span class="nt">&gt;</span>Submit<span class="nt">&lt;/button&gt;</span>
  <span class="nt">&lt;/fieldset&gt;</span>
<span class="nt">&lt;/form&gt;</span>
</code></pre></div>


                <h2 id="forms-control-readonly">只读输入框</h2>
                <p>为输入框设置 <code>readonly</code> 属性可以禁止用户输入，并且输入框的样式也是禁用状态。</p>
                <div class="bs-example">
                    <form role="form">
                        <input class="form-control" type="text" placeholder="Readonly input here…" readonly="">
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">placeholder=</span><span class="s">"Readonly input here…"</span> <span class="na">readonly</span><span class="nt">&gt;</span>
</code></pre></div>


                <h2 id="forms-control-validation">校验状态</h2>
                <p>Bootstrap 对表单控件的校验状态，如 error、warning 和 success 状态，都定义了样式。使用时，添加 <code>.has-warning</code>、<code>.has-error</code> 或 <code>.has-success</code> 类到这些控件的父元素即可。任何包含在此元素之内的 <code>.control-label</code>、<code>.form-control</code> 和 <code>.help-block</code> 元素都将接受这些校验状态的样式。</p>

                <div class="bs-example">
                    <form role="form">
                        <div class="form-group has-success">
                            <label class="control-label" for="inputSuccess1">Input with success</label>
                            <input type="text" class="form-control" id="inputSuccess1">
                        </div>
                        <div class="form-group has-warning">
                            <label class="control-label" for="inputWarning1">Input with warning</label>
                            <input type="text" class="form-control" id="inputWarning1">
                        </div>
                        <div class="form-group has-error">
                            <label class="control-label" for="inputError1">Input with error</label>
                            <input type="text" class="form-control" id="inputError1">
                        </div>
                        <div class="has-success">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="checkboxSuccess" value="option1">
                                    Checkbox with success
                                </label>
                            </div>
                        </div>
                        <div class="has-warning">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="checkboxWarning" value="option1">
                                    Checkbox with warning
                                </label>
                            </div>
                        </div>
                        <div class="has-error">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="checkboxError" value="option1">
                                    Checkbox with error
                                </label>
                            </div>
                        </div>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group has-success"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"control-label"</span> <span class="na">for=</span><span class="s">"inputSuccess1"</span><span class="nt">&gt;</span>Input with success<span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputSuccess1"</span><span class="nt">&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group has-warning"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"control-label"</span> <span class="na">for=</span><span class="s">"inputWarning1"</span><span class="nt">&gt;</span>Input with warning<span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputWarning1"</span><span class="nt">&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group has-error"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"control-label"</span> <span class="na">for=</span><span class="s">"inputError1"</span><span class="nt">&gt;</span>Input with error<span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputError1"</span><span class="nt">&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"has-success"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span> <span class="na">id=</span><span class="s">"checkboxSuccess"</span> <span class="na">value=</span><span class="s">"option1"</span><span class="nt">&gt;</span>
      Checkbox with success
    <span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"has-warning"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span> <span class="na">id=</span><span class="s">"checkboxWarning"</span> <span class="na">value=</span><span class="s">"option1"</span><span class="nt">&gt;</span>
      Checkbox with warning
    <span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"has-error"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"checkbox"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"checkbox"</span> <span class="na">id=</span><span class="s">"checkboxError"</span> <span class="na">value=</span><span class="s">"option1"</span><span class="nt">&gt;</span>
      Checkbox with error
    <span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h3>添加额外的图标</h3>
                <p>你还可以针对校验状态为输入框添加额外的图标。只需设置相应的 <code>.has-feedback</code> 类并添加正确的图标即可。</p>
                <p><strong class="text-danger">Feedback icons only work with textual <code>&lt;input class="form-control"&gt;</code> elements.</strong></p>
                <div class="bs-callout bs-callout-warning">
                    <h4>图标、<code>label</code>  和输入控件组</h4>
                    <p>对于不带有 <code>label</code> 标签的输入框以及右侧带有附加组件的<a href="../components#input-groups">输入框组</a>，需要手动为其图标定位。为了让所有用户都能访问你的网站，我们强烈建议为所有输入框添加 <code>label</code> 标签。如果你不希望将 <code>label</code> 标签展示出来，可以通过添加 <code>sr-only</code> 类来实现。如果的确不能添加 <code>label</code> 标签，请调整图标的 <code>top</code> 值。对于输入框组，请根据你的实际情况调整 <code>right</code> 值。</p>
                </div>
                <div class="bs-example">
                    <form role="form">
                        <div class="form-group has-success has-feedback">
                            <label class="control-label" for="inputSuccess2">Input with success</label>
                            <input type="text" class="form-control" id="inputSuccess2">
                            <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                        </div>
                        <div class="form-group has-warning has-feedback">
                            <label class="control-label" for="inputWarning2">Input with warning</label>
                            <input type="text" class="form-control" id="inputWarning2">
                            <span class="glyphicon glyphicon-warning-sign form-control-feedback"></span>
                        </div>
                        <div class="form-group has-error has-feedback">
                            <label class="control-label" for="inputError2">Input with error</label>
                            <input type="text" class="form-control" id="inputError2">
                            <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                        </div>
                    </form>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group has-success has-feedback"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"control-label"</span> <span class="na">for=</span><span class="s">"inputSuccess2"</span><span class="nt">&gt;</span>Input with success<span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputSuccess2"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;span</span> <span class="na">class=</span><span class="s">"glyphicon glyphicon-ok form-control-feedback"</span><span class="nt">&gt;&lt;/span&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group has-warning has-feedback"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"control-label"</span> <span class="na">for=</span><span class="s">"inputWarning2"</span><span class="nt">&gt;</span>Input with warning<span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputWarning2"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;span</span> <span class="na">class=</span><span class="s">"glyphicon glyphicon-warning-sign form-control-feedback"</span><span class="nt">&gt;&lt;/span&gt;</span>
<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group has-error has-feedback"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"control-label"</span> <span class="na">for=</span><span class="s">"inputError2"</span><span class="nt">&gt;</span>Input with error<span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputError2"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;span</span> <span class="na">class=</span><span class="s">"glyphicon glyphicon-remove form-control-feedback"</span><span class="nt">&gt;&lt;/span&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h4>为水平排列的表单和内联表单设置可选的图标</h4>
                <div class="bs-example">
                    <form class="form-horizontal" role="form">
                        <div class="form-group has-success has-feedback">
                            <label class="control-label col-sm-3" for="inputSuccess3">Input with success</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="inputSuccess3">
                                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;form</span> <span class="na">class=</span><span class="s">"form-horizontal"</span> <span class="na">role=</span><span class="s">"form"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group has-success has-feedback"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"control-label col-sm-3"</span> <span class="na">for=</span><span class="s">"inputSuccess3"</span><span class="nt">&gt;</span>Input with success<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-9"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputSuccess3"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;span</span> <span class="na">class=</span><span class="s">"glyphicon glyphicon-ok form-control-feedback"</span><span class="nt">&gt;&lt;/span&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/form&gt;</span>
</code></pre></div>

                <div class="bs-example">
                    <form class="form-inline" role="form">
                        <div class="form-group has-success has-feedback">
                            <label class="control-label" for="inputSuccess4">Input with success</label>
                            <input type="text" class="form-control" id="inputSuccess4">
                            <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                        </div>
                    </form>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;form</span> <span class="na">class=</span><span class="s">"form-inline"</span> <span class="na">role=</span><span class="s">"form"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group has-success has-feedback"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"control-label"</span> <span class="na">for=</span><span class="s">"inputSuccess4"</span><span class="nt">&gt;</span>Input with success<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputSuccess4"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;span</span> <span class="na">class=</span><span class="s">"glyphicon glyphicon-ok form-control-feedback"</span><span class="nt">&gt;&lt;/span&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/form&gt;</span>
</code></pre></div>

                <h4>可选的图标与设置 <code>.sr-only</code> 类的 <code>label</code> </h4>
                <p>通过为 <code>label</code> 元素添加 <code>.sr-only</code> 类，可以让表单控件的 <code>label</code> 元素不可见。在这种情况下，Bootstrap 将自动调整图标的位置。</p>
                <div class="bs-example">
                    <div class="form-group has-success has-feedback">
                        <label class="control-label sr-only" for="inputSuccess5">Hidden label</label>
                        <input type="text" class="form-control" id="inputSuccess5">
                        <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group has-success has-feedback"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"control-label sr-only"</span> <span class="na">for=</span><span class="s">"inputSuccess5"</span><span class="nt">&gt;</span>Hidden label<span class="nt">&lt;/label&gt;</span>
  <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">id=</span><span class="s">"inputSuccess5"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;span</span> <span class="na">class=</span><span class="s">"glyphicon glyphicon-ok form-control-feedback"</span><span class="nt">&gt;&lt;/span&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>


                <h2 id="forms-control-sizes">控件尺寸</h2>
                <p>通过 <code>.input-lg</code> 类似的类可以为控件设置高度，通过 <code>.col-lg-*</code> 类似的类可以为控件设置宽度。</p>

                <h3>高度尺寸</h3>
                <p>创建大一些或小一些的表单控件以匹配按钮尺寸。</p>
                <div class="bs-example bs-example-control-sizing">
                    <form role="form">
                        <div class="controls">
                            <input class="form-control input-lg" type="text" placeholder=".input-lg">
                            <input type="text" class="form-control" placeholder="Default input">
                            <input class="form-control input-sm" type="text" placeholder=".input-sm">

                            <select class="form-control input-lg">
                                <option value="">.input-lg</option>
                            </select>
                            <select class="form-control">
                                <option value="">Default select</option>
                            </select>
                            <select class="form-control input-sm">
                                <option value="">.input-sm</option>
                            </select>
                        </div>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"form-control input-lg"</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">placeholder=</span><span class="s">".input-lg"</span><span class="nt">&gt;</span>
<span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">placeholder=</span><span class="s">"Default input"</span><span class="nt">&gt;</span>
<span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"form-control input-sm"</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">placeholder=</span><span class="s">".input-sm"</span><span class="nt">&gt;</span>

<span class="nt">&lt;select</span> <span class="na">class=</span><span class="s">"form-control input-lg"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/select&gt;</span>
<span class="nt">&lt;select</span> <span class="na">class=</span><span class="s">"form-control"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/select&gt;</span>
<span class="nt">&lt;select</span> <span class="na">class=</span><span class="s">"form-control input-sm"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/select&gt;</span>
</code></pre></div>

                <h3>水平排列的表单组的尺寸</h3>
                <p>通过添加 <code>.form-group-lg</code> 或 <code>.form-group-sm</code> 类，为 <code>.form-horizontal</code> 包裹的 <code>label</code> 元素和表单控件快速设置尺寸。</p>
                <div class="bs-example">
                    <form class="form-horizontal" role="form">
                        <div class="form-group form-group-lg">
                            <label class="col-sm-2 control-label" for="formGroupInputLarge">Large label</label>
                            <div class="col-sm-10">
                                <input class="form-control" type="text" id="formGroupInputLarge" placeholder="Large input">
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label" for="formGroupInputSmall">Small label</label>
                            <div class="col-sm-10">
                                <input class="form-control" type="text" id="formGroupInputSmall" placeholder="Small input">
                            </div>
                        </div>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;form</span> <span class="na">class=</span><span class="s">"form-horizontal"</span> <span class="na">role=</span><span class="s">"form"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group form-group-lg"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"col-sm-2 control-label"</span> <span class="na">for=</span><span class="s">"formGroupInputLarge"</span><span class="nt">&gt;</span>Large label<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-10"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">id=</span><span class="s">"formGroupInputLarge"</span> <span class="na">placeholder=</span><span class="s">"Large input"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"form-group form-group-sm"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;label</span> <span class="na">class=</span><span class="s">"col-sm-2 control-label"</span> <span class="na">for=</span><span class="s">"formGroupInputSmall"</span><span class="nt">&gt;</span>Small label<span class="nt">&lt;/label&gt;</span>
    <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-sm-10"</span><span class="nt">&gt;</span>
      <span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">id=</span><span class="s">"formGroupInputSmall"</span> <span class="na">placeholder=</span><span class="s">"Small input"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/form&gt;</span>
</code></pre></div>


                <h3>调整列（column）尺寸</h3>
                <p>用栅格系统中的列（column）包裹输入框或其任何父元素，都可很容易的为其设置宽度。</p>
                <div class="bs-example">
                    <form role="form">
                        <div class="row">
                            <div class="col-xs-2">
                                <input type="text" class="form-control" placeholder=".col-xs-2">
                            </div>
                            <div class="col-xs-3">
                                <input type="text" class="form-control" placeholder=".col-xs-3">
                            </div>
                            <div class="col-xs-4">
                                <input type="text" class="form-control" placeholder=".col-xs-4">
                            </div>
                        </div>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"row"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-2"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">placeholder=</span><span class="s">".col-xs-2"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-3"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">placeholder=</span><span class="s">".col-xs-3"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"col-xs-4"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;input</span> <span class="na">type=</span><span class="s">"text"</span> <span class="na">class=</span><span class="s">"form-control"</span> <span class="na">placeholder=</span><span class="s">".col-xs-4"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/div&gt;</span>
</code></pre></div>

                <h2 id="forms-help-text">辅助文本</h2>
                <p>针对表单控件的“块（block）”级辅助文本。</p>
                <div class="bs-example">
                    <form role="form">
                        <input type="text" class="form-control">
                        <span class="help-block">A block of help text that breaks onto a new line and may extend beyond one line.</span>
                    </form>
                </div><!-- /.bs-example -->
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;span</span> <span class="na">class=</span><span class="s">"help-block"</span><span class="nt">&gt;</span>A block of help text that breaks onto a new line and may extend beyond one line.<span class="nt">&lt;/span&gt;</span>
</code></pre></div>
            </div>

            <div class="bs-docs-section">
                <h1 id="buttons" class="page-header">按钮</h1>

                <h2 id="buttons-options">预定义样式</h2>
                <p>使用下面列出的类可以快速创建一个带有预定义样式的按钮。</p>
                <div class="bs-example">
                    <button type="button" class="btn btn-default">Default</button>
                    <button type="button" class="btn btn-primary">Primary</button>
                    <button type="button" class="btn btn-success">Success</button>
                    <button type="button" class="btn btn-info">Info</button>
                    <button type="button" class="btn btn-warning">Warning</button>
                    <button type="button" class="btn btn-danger">Danger</button>
                    <button type="button" class="btn btn-link">Link</button>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="c">&lt;!-- Standard button --&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-default"</span><span class="nt">&gt;</span>Default<span class="nt">&lt;/button&gt;</span>

<span class="c">&lt;!-- Provides extra visual weight and identifies the primary action in a set of buttons --&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-primary"</span><span class="nt">&gt;</span>Primary<span class="nt">&lt;/button&gt;</span>

<span class="c">&lt;!-- Indicates a successful or positive action --&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-success"</span><span class="nt">&gt;</span>Success<span class="nt">&lt;/button&gt;</span>

<span class="c">&lt;!-- Contextual button for informational alert messages --&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-info"</span><span class="nt">&gt;</span>Info<span class="nt">&lt;/button&gt;</span>

<span class="c">&lt;!-- Indicates caution should be taken with this action --&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-warning"</span><span class="nt">&gt;</span>Warning<span class="nt">&lt;/button&gt;</span>

<span class="c">&lt;!-- Indicates a dangerous or potentially negative action --&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-danger"</span><span class="nt">&gt;</span>Danger<span class="nt">&lt;/button&gt;</span>

<span class="c">&lt;!-- Deemphasize a button by making it look like a link while maintaining button behavior --&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-link"</span><span class="nt">&gt;</span>Link<span class="nt">&lt;/button&gt;</span>
</code></pre></div>

                <h2 id="buttons-sizes">尺寸</h2>
                <p>需要让按钮具有不同尺寸吗？使用 <code>.btn-lg</code>、<code>.btn-sm</code> 或 <code>.btn-xs</code> 可以获得不同尺寸的按钮。</p>
                <div class="bs-example">
                    <p>
                        <button type="button" class="btn btn-primary btn-lg">Large button</button>
                        <button type="button" class="btn btn-default btn-lg">Large button</button>
                    </p>
                    <p>
                        <button type="button" class="btn btn-primary">Default button</button>
                        <button type="button" class="btn btn-default">Default button</button>
                    </p>
                    <p>
                        <button type="button" class="btn btn-primary btn-sm">Small button</button>
                        <button type="button" class="btn btn-default btn-sm">Small button</button>
                    </p>
                    <p>
                        <button type="button" class="btn btn-primary btn-xs">Extra small button</button>
                        <button type="button" class="btn btn-default btn-xs">Extra small button</button>
                    </p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;p&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-primary btn-lg"</span><span class="nt">&gt;</span>Large button<span class="nt">&lt;/button&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-default btn-lg"</span><span class="nt">&gt;</span>Large button<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-primary"</span><span class="nt">&gt;</span>Default button<span class="nt">&lt;/button&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-default"</span><span class="nt">&gt;</span>Default button<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-primary btn-sm"</span><span class="nt">&gt;</span>Small button<span class="nt">&lt;/button&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-default btn-sm"</span><span class="nt">&gt;</span>Small button<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-primary btn-xs"</span><span class="nt">&gt;</span>Extra small button<span class="nt">&lt;/button&gt;</span>
  <span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-default btn-xs"</span><span class="nt">&gt;</span>Extra small button<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;/p&gt;</span>
</code></pre></div>

                <p>通过给按钮添加 <code>.btn-block</code> 类可以将其拉伸至父元素100%的宽度，而且按钮也变为了块级（block）元素。</p>
                <div class="bs-example">
                    <div class="well" style="max-width: 400px; margin: 0 auto 10px;">
                        <button type="button" class="btn btn-primary btn-lg btn-block">Block level button</button>
                        <button type="button" class="btn btn-default btn-lg btn-block">Block level button</button>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-primary btn-lg btn-block"</span><span class="nt">&gt;</span>Block level button<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-default btn-lg btn-block"</span><span class="nt">&gt;</span>Block level button<span class="nt">&lt;/button&gt;</span>
</code></pre></div>


                <h2 id="buttons-active">激活状态</h2>
                <p>当按钮处于激活状态时，其表现为被按压下去（底色更深、边框夜色更深、向内投射阴影）。对于 <code>&lt;button&gt;</code> 元素，是通过 <code>:active</code> 状态实现的。对于 <code>&lt;a&gt;</code> 元素，是通过 <code>.active</code> 类实现的。然而，你还可以将 <code>.active</code> 应用到 <code>&lt;button&gt;</code> 上，并通过编程的方式使其处于激活状态。</p>

                <h3>button 元素</h3>
                <p>由于 <code>:active</code> 是伪状态，因此无需额外添加，但是在需要让其表现出同样外观的时候可以添加 <code>.active</code> 类。</p>
                <p class="bs-example">
                    <button type="button" class="btn btn-primary btn-lg active">Primary button</button>
                    <button type="button" class="btn btn-default btn-lg active">Button</button>
                </p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-primary btn-lg active"</span><span class="nt">&gt;</span>Primary button<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-default btn-lg active"</span><span class="nt">&gt;</span>Button<span class="nt">&lt;/button&gt;</span>
</code></pre></div>

                <h3>链接（<code>&lt;a&gt;</code>）元素</h3>
                <p>可以为基于 <code>&lt;a&gt;</code> 元素创建的按钮添加 <code>.active</code> 类。</p>
                <p class="bs-example">
                    <a href="#" class="btn btn-primary btn-lg active" role="button">Primary link</a>
                    <a href="#" class="btn btn-default btn-lg active" role="button">Link</a>
                </p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;a</span> <span class="na">href=</span><span class="s">"#"</span> <span class="na">class=</span><span class="s">"btn btn-primary btn-lg active"</span> <span class="na">role=</span><span class="s">"button"</span><span class="nt">&gt;</span>Primary link<span class="nt">&lt;/a&gt;</span>
<span class="nt">&lt;a</span> <span class="na">href=</span><span class="s">"#"</span> <span class="na">class=</span><span class="s">"btn btn-default btn-lg active"</span> <span class="na">role=</span><span class="s">"button"</span><span class="nt">&gt;</span>Link<span class="nt">&lt;/a&gt;</span>
</code></pre></div>


                <h2 id="buttons-disabled">禁用状态</h2>
                <p>通过为按钮的背景设置 <code>opacity</code> 属性就可以呈现出无法点击的效果。</p>

                <h3>button 元素</h3>
                <p>为 <code>&lt;button&gt;</code> 元素添加 <code>disabled</code> 属性，使其表现出禁用状态。</p>
                <p class="bs-example">
                    <button type="button" class="btn btn-primary btn-lg" disabled="disabled">Primary button</button>
                    <button type="button" class="btn btn-default btn-lg" disabled="disabled">Button</button>
                </p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-lg btn-primary"</span> <span class="na">disabled=</span><span class="s">"disabled"</span><span class="nt">&gt;</span>Primary button<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"btn btn-default btn-lg"</span> <span class="na">disabled=</span><span class="s">"disabled"</span><span class="nt">&gt;</span>Button<span class="nt">&lt;/button&gt;</span>
</code></pre></div>

                <div class="bs-callout bs-callout-danger">
                    <h4>跨浏览器兼容性</h4>
                    <p>如果为 <code>&lt;button&gt;</code> 元素添加 <code>disabled</code> 属性，Internet Explorer 9 及更低版本的浏览器将会把按钮中的文本绘制为灰色，并带有恶心的阴影，目前我们还没有解决办法。</p>
                </div>

                <h3>链接（<code>&lt;a&gt;</code>）元素</h3>
                <p>为基于 <code>&lt;a&gt;</code> 元素创建的按钮添加 <code>.disabled</code> 类。</p>
                <p class="bs-example">
                    <a href="#" class="btn btn-primary btn-lg disabled" role="button">Primary link</a>
                    <a href="#" class="btn btn-default btn-lg disabled" role="button">Link</a>
                </p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;a</span> <span class="na">href=</span><span class="s">"#"</span> <span class="na">class=</span><span class="s">"btn btn-primary btn-lg disabled"</span> <span class="na">role=</span><span class="s">"button"</span><span class="nt">&gt;</span>Primary link<span class="nt">&lt;/a&gt;</span>
<span class="nt">&lt;a</span> <span class="na">href=</span><span class="s">"#"</span> <span class="na">class=</span><span class="s">"btn btn-default btn-lg disabled"</span> <span class="na">role=</span><span class="s">"button"</span><span class="nt">&gt;</span>Link<span class="nt">&lt;/a&gt;</span>
</code></pre></div>
                <p>
                    我们把 <code>.disabled</code> 作为工具类使用，就像 <code>.active</code> 类一样，因此不需要增加前缀。
                </p>
                <div class="bs-callout bs-callout-warning">
                    <h4>链接的原始功能不受影响</h4>
                    <p>上面提到的类只是通过设置 <code>pointer-events: none</code> 来禁止 <code>&lt;a&gt;</code> 元素作为链接的原始功能，但是，这一 CSS 属性并没有被标准化，并且 Opera 18 及更低版本的浏览器并没有完全支持这一属性，同样，Internet Explorer 11 也不支持。因此，为了安全起见，建议通过 JavaScript 代码来禁止链接的原始功能。</p>
                </div>
                <div class="bs-callout bs-callout-warning">
                    <h4>Context-specific usage</h4>
                    <p>虽然按钮类可以应用到 <code>&lt;a&gt;</code> 和 <code>&lt;button&gt;</code> 元素上，但是，导航和导航条只支持 <code>&lt;button&gt;</code> 元素。</p>
                </div>


                <h2 id="buttons-tags">按钮类</h2>
                <p>为 <code>&lt;a&gt;</code>、<code>&lt;button&gt;</code> 或 <code>&lt;input&gt;</code> 元素应用按钮类。</p>
                <form class="bs-example">
                    <a class="btn btn-default" href="#" role="button">Link</a>
                    <button class="btn btn-default" type="submit">Button</button>
                    <input class="btn btn-default" type="button" value="Input">
                    <input class="btn btn-default" type="submit" value="Submit">
                </form>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;a</span> <span class="na">class=</span><span class="s">"btn btn-default"</span> <span class="na">href=</span><span class="s">"#"</span> <span class="na">role=</span><span class="s">"button"</span><span class="nt">&gt;</span>Link<span class="nt">&lt;/a&gt;</span>
<span class="nt">&lt;button</span> <span class="na">class=</span><span class="s">"btn btn-default"</span> <span class="na">type=</span><span class="s">"submit"</span><span class="nt">&gt;</span>Button<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"btn btn-default"</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">value=</span><span class="s">"Input"</span><span class="nt">&gt;</span>
<span class="nt">&lt;input</span> <span class="na">class=</span><span class="s">"btn btn-default"</span> <span class="na">type=</span><span class="s">"submit"</span> <span class="na">value=</span><span class="s">"Submit"</span><span class="nt">&gt;</span>
</code></pre></div>

                <div class="bs-callout bs-callout-warning">
                    <h4>跨浏览器展现</h4>
                    <p>我们总结的最佳实践是，<strong>强烈建议尽可能使用 <code>&lt;button&gt;</code> 元素</strong>来获得在各个浏览器上获得相匹配的绘制效果。</p>
                    <p>另外，我们还发现了<a href="https://bugzilla.mozilla.org/show_bug.cgi?id=697451">a bug in Firefox &lt;30 版本的浏览器上出现的一个 bug</a> ：阻止我们为基于 <code>&lt;input&gt;</code> 元素创建的按钮设置 <code>line-height</code> 属性，这就导致在 Firefox 浏览器上不能完全和其他按钮保持一致的高度。</p>
                </div>
            </div>

            <div class="bs-docs-section">
                <h1 id="images" class="page-header">图片</h1>

                <h2 id="images-responsive">响应式图片</h2>
                <p>在 Bootstrap 版本 3 中，通过为图片添加 <code>.img-responsive</code> 类可以让图片支持响应式布局。其实质是为图片设置了 <code>max-width: 100%;</code> 和 <code>height: auto;</code> 属性，从而让图片在其父元素中更好的缩放。</p>
                <div class="bs-callout bs-callout-warning">
                    <h4>SVG 图像和 IE 8-10</h4>
                    <p>在 Internet Explorer 8-10 中，设置为 <code>.img-responsive</code> 的 SVG 图像显示出的尺寸不匀称。为了解决这个问题，在出问题的地方添加 <code>width: 100% \9;</code> 即可。Bootstrap 并没有自动为所有图像元素设置这一属性，因为这会导致其他图像格式出现错乱。</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;img</span> <span class="na">src=</span><span class="s">"..."</span> <span class="na">class=</span><span class="s">"img-responsive"</span> <span class="na">alt=</span><span class="s">"Responsive image"</span><span class="nt">&gt;</span>
</code></pre></div>

                <h2 id="images-shapes">图片形状</h2>
                <p>通过为 <code>&lt;img&gt;</code> 元素添加以下相应的类，可以让图片呈现不同的形状。</p>
                <div class="bs-callout bs-callout-danger">
                    <h4>跨浏览器兼容性</h4>
                    <p>请时刻牢记：Internet Explorer 8 不支持 CSS3 中的圆角属性。</p>
                </div>
                <div class="bs-example bs-example-images">
                    <img data-src="holder.js/140x140" class="img-rounded" alt="140x140" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" data-holder-rendered="true" style="width: 140px; height: 140px;">
                    <img data-src="holder.js/140x140" class="img-circle" alt="140x140" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" data-holder-rendered="true" style="width: 140px; height: 140px;">
                    <img data-src="holder.js/140x140" class="img-thumbnail" alt="140x140" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" data-holder-rendered="true" style="width: 140px; height: 140px;">
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;img</span> <span class="na">src=</span><span class="s">"..."</span> <span class="na">alt=</span><span class="s">"..."</span> <span class="na">class=</span><span class="s">"img-rounded"</span><span class="nt">&gt;</span>
<span class="nt">&lt;img</span> <span class="na">src=</span><span class="s">"..."</span> <span class="na">alt=</span><span class="s">"..."</span> <span class="na">class=</span><span class="s">"img-circle"</span><span class="nt">&gt;</span>
<span class="nt">&lt;img</span> <span class="na">src=</span><span class="s">"..."</span> <span class="na">alt=</span><span class="s">"..."</span> <span class="na">class=</span><span class="s">"img-thumbnail"</span><span class="nt">&gt;</span>
</code></pre></div>
            </div>

            <div class="bs-docs-section">
                <h1 id="helper-classes" class="page-header">辅助类</h1>

                <h3 id="helper-classes-colors">Contextual colors</h3>
                <p>Convey meaning through color with a handful of emphasis utility classes. These may also be applied to links and will darken on hover just like our default link styles.</p>
                <div class="bs-example">
                    <p class="text-muted">Fusce dapibus, tellus ac cursus commodo, tortor mauris nibh.</p>
                    <p class="text-primary">Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p class="text-success">Duis mollis, est non commodo luctus, nisi erat porttitor ligula.</p>
                    <p class="text-info">Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
                    <p class="text-warning">Etiam porta sem malesuada magna mollis euismod.</p>
                    <p class="text-danger">Donec ullamcorper nulla non metus auctor fringilla.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-muted"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-primary"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-success"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-info"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-warning"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"text-danger"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
</code></pre></div>
                <div class="bs-callout bs-callout-info">
                    <h4>Dealing with specificity</h4>
                    <p>Sometimes emphasis classes cannot be applied due to the specificity of another selector. In most cases, a sufficient workaround is to wrap your text in a <code>&lt;span&gt;</code> with the class.</p>
                </div>

                <h3 id="helper-classes-backgrounds">Contextual backgrounds</h3>
                <p>Similar to the contextual text color classes, easily set the background of an element to any contextual class. Anchor components will darken on hover, just like the text classes.</p>
                <div class="bs-example bs-example-bg-classes">
                    <p class="bg-primary">Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p class="bg-success">Duis mollis, est non commodo luctus, nisi erat porttitor ligula.</p>
                    <p class="bg-info">Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
                    <p class="bg-warning">Etiam porta sem malesuada magna mollis euismod.</p>
                    <p class="bg-danger">Donec ullamcorper nulla non metus auctor fringilla.</p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"bg-primary"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"bg-success"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"bg-info"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"bg-warning"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
<span class="nt">&lt;p</span> <span class="na">class=</span><span class="s">"bg-danger"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/p&gt;</span>
</code></pre></div>
                <div class="bs-callout bs-callout-info">
                    <h4>Dealing with specificity</h4>
                    <p>Sometimes contextual background classes cannot be applied due to the specificity of another selector. In some cases, a sufficient workaround is to wrap your element's content in a <code>&lt;div&gt;</code> with the class.</p>
                </div>

                <h3 id="helper-classes-close">关闭按钮</h3>
                <p>通过使用一个象征关闭的图标，可以让模态框和警告框消失。</p>
                <div class="bs-example">
                    <p><button type="button" class="close"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button></p>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;button</span> <span class="na">type=</span><span class="s">"button"</span> <span class="na">class=</span><span class="s">"close"</span><span class="nt">&gt;&lt;span</span> <span class="na">aria-hidden=</span><span class="s">"true"</span><span class="nt">&gt;</span><span class="ni">&amp;times;</span><span class="nt">&lt;/span&gt;&lt;span</span> <span class="na">class=</span><span class="s">"sr-only"</span><span class="nt">&gt;</span>Close<span class="nt">&lt;/span&gt;&lt;/button&gt;</span>
</code></pre></div>


                <h3 id="helper-classes-carets">三角符号</h3>
                <p>通过使用三角符号可以指示某个元素具有下拉菜单的功能。注意，<a href="../components/#btn-dropdowns-dropup">向上弹出式菜单</a>中的三角符号是反方向的。</p>
                <div class="bs-example">
                    <span class="caret"></span>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;span</span> <span class="na">class=</span><span class="s">"caret"</span><span class="nt">&gt;&lt;/span&gt;</span>
</code></pre></div>


                <h3 id="helper-classes-floats">快速浮动</h3>
                <p>Float an element to the left or right with a class. <code>!important</code> is included to avoid specificity issues. Classes can also be used as mixins.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"pull-left"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"pull-right"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/div&gt;</span>
</code></pre></div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Classes</span>
<span class="nc">.pull-left</span> <span class="p">{</span>
  <span class="na">float</span><span class="o">:</span> <span class="no">left</span> <span class="nv">!important</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.pull-right</span> <span class="p">{</span>
  <span class="na">float</span><span class="o">:</span> <span class="no">right</span> <span class="nv">!important</span><span class="p">;</span>
<span class="p">}</span>

<span class="c1">// Usage as mixins</span>
<span class="nc">.element</span> <span class="p">{</span>
  <span class="nc">.pull-left</span><span class="o">();</span>
<span class="p">}</span>
<span class="nc">.another-element</span> <span class="p">{</span>
  <span class="nc">.pull-right</span><span class="o">();</span>
<span class="p">}</span>
</code></pre></div>

                <div class="bs-callout bs-callout-warning">
                    <h4>Not for use in navbars</h4>
                    <p>To align components in navbars with utility classes, use <code>.navbar-left</code> or <code>.navbar-right</code> instead. <a href="../components/#navbar-component-alignment">See the navbar docs</a> for details.</p>
                </div>


                <h3 id="helper-classes-center">Center content blocks</h3>
                <p>Set an element to <code>display: block</code> and center via <code>margin</code>. Available as a mixin and class.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"center-block"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/div&gt;</span>
</code></pre></div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Classes</span>
<span class="nc">.center-block</span> <span class="p">{</span>
  <span class="na">display</span><span class="o">:</span> <span class="no">block</span><span class="p">;</span>
  <span class="na">margin-left</span><span class="o">:</span> <span class="no">auto</span><span class="p">;</span>
  <span class="na">margin-right</span><span class="o">:</span> <span class="no">auto</span><span class="p">;</span>
<span class="p">}</span>

<span class="c1">// Usage as mixins</span>
<span class="nc">.element</span> <span class="p">{</span>
  <span class="nc">.center-block</span><span class="o">();</span>
<span class="p">}</span>
</code></pre></div>


                <h3 id="helper-classes-clearfix">Clearfix</h3>
                <p>Easily clear <code>float</code>s by adding <code>.clearfix</code> <strong>to the parent element</strong>. Utilizes <a href="http://nicolasgallagher.com/micro-clearfix-hack/">the micro clearfix</a> as popularized by Nicolas Gallagher. Can also be used as a mixin.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="c">&lt;!-- Usage as a class --&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"clearfix"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/div&gt;</span>
</code></pre></div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Mixin itself</span>
<span class="nc">.clearfix</span><span class="o">()</span> <span class="p">{</span>
  <span class="k">&amp;</span><span class="nd">:before</span><span class="o">,</span>
  <span class="k">&amp;</span><span class="nd">:after</span> <span class="p">{</span>
    <span class="na">content</span><span class="o">:</span> <span class="s2">" "</span><span class="p">;</span>
    <span class="na">display</span><span class="o">:</span> <span class="n">table</span><span class="p">;</span>
  <span class="p">}</span>
  <span class="k">&amp;</span><span class="nd">:after</span> <span class="p">{</span>
    <span class="na">clear</span><span class="o">:</span> <span class="no">both</span><span class="p">;</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Usage as a Mixin</span>
<span class="nc">.element</span> <span class="p">{</span>
  <span class="nc">.clearfix</span><span class="o">();</span>
<span class="p">}</span>
</code></pre></div>


                <h3 id="helper-classes-show-hide">Showing and hiding content</h3>
                <p>Force an element to be shown or hidden (<strong>including for screen readers</strong>) with the use of <code>.show</code> and <code>.hidden</code> classes. These classes use <code>!important</code> to avoid specificity conflicts, just like the <a href="#helper-classes-floats">quick floats</a>. They are only available for block level toggling. They can also be used as mixins.</p>
                <p><code>.hide</code> is available, but it does not always affect screen readers and is <strong>deprecated</strong> as of v3.0.1. Use <code>.hidden</code> or <code>.sr-only</code> instead.</p>
                <p>Furthermore, <code>.invisible</code> can be used to toggle only the visibility of an element, meaning its <code>display</code> is not modified and the element can still affect the flow of the document.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"show"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"hidden"</span><span class="nt">&gt;</span>...<span class="nt">&lt;/div&gt;</span>
</code></pre></div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Classes</span>
<span class="nc">.show</span> <span class="p">{</span>
  <span class="na">display</span><span class="o">:</span> <span class="no">block</span> <span class="nv">!important</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.hidden</span> <span class="p">{</span>
  <span class="na">display</span><span class="o">:</span> <span class="no">none</span> <span class="nv">!important</span><span class="p">;</span>
  <span class="na">visibility</span><span class="o">:</span> <span class="no">hidden</span> <span class="nv">!important</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.invisible</span> <span class="p">{</span>
  <span class="na">visibility</span><span class="o">:</span> <span class="no">hidden</span><span class="p">;</span>
<span class="p">}</span>

<span class="c1">// Usage as mixins</span>
<span class="nc">.element</span> <span class="p">{</span>
  <span class="nc">.show</span><span class="o">();</span>
<span class="p">}</span>
<span class="nc">.another-element</span> <span class="p">{</span>
  <span class="nc">.hidden</span><span class="o">();</span>
<span class="p">}</span>
</code></pre></div>


                <h3 id="helper-classes-screen-readers">Screen reader and keyboard navigation content</h3>
                <p>Hide an element to all devices <strong>except screen readers</strong> with <code>.sr-only</code>. Combine <code>.sr-only</code> with <code>.sr-only-focusable</code> to show the element again when it's focused (e.g. by a keyboard-only user). Necessary for following <a href="../getting-started/#accessibility">accessibility best practices</a>. Can also be used as mixins.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;a</span> <span class="na">class=</span><span class="s">"sr-only sr-only-focusable"</span> <span class="na">href=</span><span class="s">"#content"</span><span class="nt">&gt;</span>Skip to main content<span class="nt">&lt;/a&gt;</span>
</code></pre></div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Usage as a Mixin</span>
<span class="nc">.skip-navigation</span> <span class="p">{</span>
  <span class="nc">.sr-only</span><span class="o">();</span>
  <span class="nc">.sr-only-focusable</span><span class="o">();</span>
<span class="p">}</span>
</code></pre></div>


                <h3 id="helper-classes-image-replacement">Image replacement</h3>
                <p>Utilize the <code>.text-hide</code> class or mixin to help replace an element's text content with a background image.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="html"><span class="nt">&lt;h1</span> <span class="na">class=</span><span class="s">"text-hide"</span><span class="nt">&gt;</span>Custom heading<span class="nt">&lt;/h1&gt;</span>
</code></pre></div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Usage as a Mixin</span>
<span class="nc">.heading</span> <span class="p">{</span>
  <span class="nc">.text-hide</span><span class="o">();</span>
<span class="p">}</span>
</code></pre></div>
            </div>

            <div class="bs-docs-section">
                <h1 id="responsive-utilities" class="page-header">响应式工具</h1>

                <p class="lead">为了加快对移动设备友好的页面开发工作，利用媒体查询功能并使用这些工具类可以方便的针对不同设备展示或隐藏页面内容。另外还包含了针对打印机显示或隐藏内容的工具类。</p>
                <p>有针对性的使用这类工具类，从而避免为同一个网站创建完全不同的版本。相反，通过使用这些工具类可以在不同设备上提供不同的展现形式。</p>


                <h2 id="responsive-utilities-classes">可用的类</h2>
                <p>通过单独或联合使用以下列出的类，可以针对不同屏幕尺寸隐藏或显示页面内容。</p>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped responsive-utilities">
                        <thead>
                        <tr>
                            <th></th>
                            <th>
                                超小屏幕
                                <small>手机 (&lt;768px)</small>
                            </th>
                            <th>
                                小屏幕
                                <small>平板 (≥768px)</small>
                            </th>
                            <th>
                                中等屏幕
                                <small>桌面 (≥992px)</small>
                            </th>
                            <th>
                                大屏幕
                                <small>桌面 (≥1200px)</small>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th><code>.visible-xs-*</code></th>
                            <td class="is-visible">可见</td>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-hidden">隐藏</td>
                        </tr>
                        <tr>
                            <th><code>.visible-sm-*</code></th>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-visible">可见</td>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-hidden">隐藏</td>
                        </tr>
                        <tr>
                            <th><code>.visible-md-*</code></th>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-visible">可见</td>
                            <td class="is-hidden">隐藏</td>
                        </tr>
                        <tr>
                            <th><code>.visible-lg-*</code></th>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-visible">可见</td>
                        </tr>
                        </tbody>
                        <tbody>
                        <tr>
                            <th><code>.hidden-xs</code></th>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-visible">可见</td>
                            <td class="is-visible">可见</td>
                            <td class="is-visible">可见</td>
                        </tr>
                        <tr>
                            <th><code>.hidden-sm</code></th>
                            <td class="is-visible">可见</td>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-visible">可见</td>
                            <td class="is-visible">可见</td>
                        </tr>
                        <tr>
                            <th><code>.hidden-md</code></th>
                            <td class="is-visible">可见</td>
                            <td class="is-visible">可见</td>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-visible">可见</td>
                        </tr>
                        <tr>
                            <th><code>.hidden-lg</code></th>
                            <td class="is-visible">可见</td>
                            <td class="is-visible">可见</td>
                            <td class="is-visible">可见</td>
                            <td class="is-hidden">隐藏</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <p>从 v3.2.0 版本起，形如 <code>.visible-*-*</code> 的类针对每种屏幕大小都有了三种变体，每个针对 CSS 中不同的 <code>display</code> 属性，列表如下：</p>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th>类组</th>
                            <th>CSS <code>display</code></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><code>.visible-*-block</code></td>
                            <td><code>display: block;</code></td>
                        </tr>
                        <tr>
                            <td><code>.visible-*-inline</code></td>
                            <td><code>display: inline;</code></td>
                        </tr>
                        <tr>
                            <td><code>.visible-*-inline-block</code></td>
                            <td><code>display: inline-block;</code></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <p>因此，以超小屏幕（<code>xs</code>）为例，可用的 <code>.visible-*-*</code> 类是：<code>.visible-xs-block</code>、<code>.visible-xs-inline</code> 和 <code>.visible-xs-inline-block</code>。</p>
                <p><code>.visible-xs</code>、<code>.visible-sm</code>、<code>.visible-md</code> 和 <code>.visible-lg</code> 类也同时存在。但是<strong>从 v3.2.0 版本开始不再建议使用</strong>。除了 <code>&lt;table&gt;</code> 相关的元素的特殊情况外，它们与 <code>.visible-*-block</code> 大体相同。</p>

                <h2 id="responsive-utilities-print">打印类</h2>
                <p>和常规的响应式类一样，使用下面的类可以针对打印机隐藏或显示某些内容。</p>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped responsive-utilities">
                        <thead>
                        <tr>
                            <th>class</th>
                            <th>浏览器</th>
                            <th>打印机</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th>
                                <code>.visible-print-block</code><br>
                                <code>.visible-print-inline</code><br>
                                <code>.visible-print-inline-block</code>
                            </th>
                            <td class="is-hidden">隐藏</td>
                            <td class="is-visible">可见</td>
                        </tr>
                        <tr>
                            <th><code>.hidden-print</code></th>
                            <td class="is-visible">可见</td>
                            <td class="is-hidden">隐藏</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <p><code>.visible-print</code> 类也是存在的，但是从 v3.2.0 版本开始<strong>不建议使用</strong>。它与 <code>.visible-print-block</code> 类大致相同，除了 <code>&lt;table&gt;</code> 相关元素的特殊情况外。</p>


                <h2 id="responsive-utilities-tests">测试用例</h2>
                <p>调整你的浏览器大小，或者用其他设备打开页面，都可以测试这些响应式工具类。</p>

                <h3>在...上可见</h3>
                <p>带有绿色标记的元素表示其在当前浏览器视口（viewport）中是<strong>可见的</strong>。</p>
                <div class="row responsive-utilities-test visible-on">
                    <div class="col-xs-6">
                        <span class="hidden-xs">超小屏幕</span>
                        <span class="visible-xs-block">✔ 在超小屏幕上可见</span>
                    </div>
                    <div class="col-xs-6">
                        <span class="hidden-sm">小屏幕</span>
                        <span class="visible-sm-block">✔ 在小屏幕上可见</span>
                    </div>
                    <div class="clearfix visible-xs-block"></div>
                    <div class="col-xs-6">
                        <span class="hidden-md">中等屏幕</span>
                        <span class="visible-md-block">✔ 在中等屏幕上可见</span>
                    </div>
                    <div class="col-xs-6">
                        <span class="hidden-lg">大屏幕</span>
                        <span class="visible-lg-block">✔ 在大屏幕上可见</span>
                    </div>
                </div>
                <div class="row responsive-utilities-test visible-on">
                    <div class="col-xs-6 col-sm-6">
                        <span class="hidden-xs hidden-sm">超小屏幕和小屏幕</span>
                        <span class="visible-xs-block visible-sm-block">✔ 在超小屏幕和小屏幕上可见</span>
                    </div>
                    <div class="col-xs-6 col-sm-6">
                        <span class="hidden-md hidden-lg">中等屏幕和大屏幕</span>
                        <span class="visible-md-block visible-lg-block">✔ 在中等屏幕和大屏幕上可见</span>
                    </div>
                    <div class="clearfix visible-xs-block"></div>
                    <div class="col-xs-6 col-sm-6">
                        <span class="hidden-xs hidden-md">超小屏幕和中等屏幕</span>
                        <span class="visible-xs-block visible-md-block">✔ 在超小屏幕和中等屏幕上可见</span>
                    </div>
                    <div class="col-xs-6 col-sm-6">
                        <span class="hidden-sm hidden-lg">小屏幕和大屏幕</span>
                        <span class="visible-sm-block visible-lg-block">✔ 在小屏幕和大屏幕上可见</span>
                    </div>
                    <div class="clearfix visible-xs-block"></div>
                    <div class="col-xs-6 col-sm-6">
                        <span class="hidden-xs hidden-lg">超小屏幕和大屏幕</span>
                        <span class="visible-xs-block visible-lg-block">✔ 在超小屏幕和大屏幕上可见</span>
                    </div>
                    <div class="col-xs-6 col-sm-6">
                        <span class="hidden-sm hidden-md">小屏幕和中等屏幕</span>
                        <span class="visible-sm-block visible-md-block">✔ 在小屏幕和中等屏幕上可见</span>
                    </div>
                </div>

                <h3>在...上隐藏</h3>
                <p>带有绿色标记的元素表示其在当前浏览器视口（viewport）中是<strong>隐藏的</strong>。</p>
                <div class="row responsive-utilities-test hidden-on">
                    <div class="col-xs-6 col-sm-3">
                        <span class="hidden-xs">超小屏幕</span>
                        <span class="visible-xs-block">✔ 在超小屏幕上隐藏</span>
                    </div>
                    <div class="col-xs-6 col-sm-3">
                        <span class="hidden-sm">小屏幕</span>
                        <span class="visible-sm-block">✔ 在小屏幕上隐藏</span>
                    </div>
                    <div class="clearfix visible-xs-block"></div>
                    <div class="col-xs-6 col-sm-3">
                        <span class="hidden-md">中等屏幕</span>
                        <span class="visible-md-block">✔ 在中等屏幕上隐藏</span>
                    </div>
                    <div class="col-xs-6 col-sm-3">
                        <span class="hidden-lg">大屏幕</span>
                        <span class="visible-lg-block">✔ 在大屏幕上隐藏</span>
                    </div>
                </div>
                <div class="row responsive-utilities-test hidden-on">
                    <div class="col-xs-6">
                        <span class="hidden-xs hidden-sm">超小屏幕与小屏幕</span>
                        <span class="visible-xs-block visible-sm-block">✔ 在超小屏幕和小屏幕上隐藏</span>
                    </div>
                    <div class="col-xs-6">
                        <span class="hidden-md hidden-lg">中等屏幕和大屏幕</span>
                        <span class="visible-md-block visible-lg-block">✔ 在 medium 和 large 上隐藏</span>
                    </div>
                    <div class="clearfix visible-xs-block"></div>
                    <div class="col-xs-6">
                        <span class="hidden-xs hidden-md">超小屏幕和中等屏幕</span>
                        <span class="visible-xs-block visible-md-block">✔ 在超小屏幕和中等屏幕上隐藏</span>
                    </div>
                    <div class="col-xs-6">
                        <span class="hidden-sm hidden-lg">小屏幕和大屏幕</span>
                        <span class="visible-sm-block visible-lg-block">✔ 在小屏幕和大屏幕上隐藏</span>
                    </div>
                    <div class="clearfix visible-xs-block"></div>
                    <div class="col-xs-6">
                        <span class="hidden-xs hidden-lg">超小屏幕和大屏幕</span>
                        <span class="visible-xs-block visible-lg-block">✔ 在超小屏幕和大屏幕上隐藏</span>
                    </div>
                    <div class="col-xs-6">
                        <span class="hidden-sm hidden-md">小屏幕和中等屏幕</span>
                        <span class="visible-sm-block visible-md-block">✔ 在小屏幕和中等屏幕上隐藏</span>
                    </div>
                </div>
            </div>

            <div class="bs-docs-section">
                <h1 id="less" class="page-header">使用 Less</h1>

                <p class="lead">Bootstrap 的 CSS 文件是通过 Less 源码编译出来的。Less 是一门预处理语言，支持变量、mixin、函数等额外功能。对于希望使用 Less 源码而非编译出来的 CSS 文件的用户，Bootstrap 框架中包含的大量变量、mixin 将非常有价值。</p>

                <p>针对栅格系统的变量和 mixin 包含在<a href="#grid-less">栅格系统</a>章节。</p>


                <h2 id="less-bootstrap">编译 Bootstrap</h2>
                <p>Bootstrap can be used in at least two ways: with the compiled CSS or with the source Less files. To compile the Less files, <a href="../getting-started/#grunt">consult the Getting Started section</a> for how to setup your development environment to run the necessary commands.</p>

                <h2 id="less-variables">变量</h2>
                <p>Variables are used throughout the entire project as a way to centralize and share commonly used values like colors, spacing, or font stacks. For a complete breakdown, please see <a href="../customize/#less-variables-section">the Customizer</a>.</p>

                <h3 id="less-variables-colors">颜色</h3>
                <p>Easily make use of two color schemes: grayscale and semantic. Grayscale colors provide quick access to commonly used shades of black while semantic include various colors assigned to meaningful contextual values.</p>
                <div class="bs-example">
                    <div class="color-swatches">
                        <div class="color-swatch gray-darker"></div>
                        <div class="color-swatch gray-dark"></div>
                        <div class="color-swatch gray"></div>
                        <div class="color-swatch gray-light"></div>
                        <div class="color-swatch gray-lighter"></div>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="k">@gray-darker</span><span class="nd">:</span>  <span class="nt">lighten</span><span class="o">(</span><span class="nn">#000</span><span class="o">,</span> <span class="nt">13</span><span class="nc">.5</span><span class="err">%</span><span class="o">);</span> <span class="o">//</span> <span class="nn">#222</span>
<span class="o">@</span><span class="nt">gray-dark</span><span class="nd">:</span>    <span class="nt">lighten</span><span class="o">(</span><span class="nn">#000</span><span class="o">,</span> <span class="nt">20</span><span class="err">%</span><span class="o">);</span>   <span class="o">//</span> <span class="nn">#333</span>
<span class="o">@</span><span class="nt">gray</span><span class="nd">:</span>         <span class="nt">lighten</span><span class="o">(</span><span class="nn">#000</span><span class="o">,</span> <span class="nt">33</span><span class="nc">.5</span><span class="err">%</span><span class="o">);</span> <span class="o">//</span> <span class="nn">#555</span>
<span class="o">@</span><span class="nt">gray-light</span><span class="nd">:</span>   <span class="nt">lighten</span><span class="o">(</span><span class="nn">#000</span><span class="o">,</span> <span class="nt">46</span><span class="nc">.7</span><span class="err">%</span><span class="o">);</span> <span class="o">//</span> <span class="nn">#777</span>
<span class="o">@</span><span class="nt">gray-lighter</span><span class="nd">:</span> <span class="nt">lighten</span><span class="o">(</span><span class="nn">#000</span><span class="o">,</span> <span class="nt">93</span><span class="nc">.5</span><span class="err">%</span><span class="o">);</span> <span class="o">//</span> <span class="nn">#eee</span>
</code></pre></div>

                <div class="bs-example">
                    <div class="color-swatches">
                        <div class="color-swatch brand-primary"></div>
                        <div class="color-swatch brand-success"></div>
                        <div class="color-swatch brand-info"></div>
                        <div class="color-swatch brand-warning"></div>
                        <div class="color-swatch brand-danger"></div>
                    </div>
                </div>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="k">@brand-primary</span><span class="nd">:</span> <span class="nn">#428bca</span><span class="o">;</span>
<span class="o">@</span><span class="nt">brand-success</span><span class="nd">:</span> <span class="nn">#5cb85c</span><span class="o">;</span>
<span class="o">@</span><span class="nt">brand-info</span><span class="nd">:</span>    <span class="nn">#5bc0de</span><span class="o">;</span>
<span class="o">@</span><span class="nt">brand-warning</span><span class="nd">:</span> <span class="nn">#f0ad4e</span><span class="o">;</span>
<span class="o">@</span><span class="nt">brand-danger</span><span class="nd">:</span>  <span class="nn">#d9534f</span><span class="o">;</span>
</code></pre></div>

                <p>Use any of these color variables as they are or reassign them to more meaningful variables for your project.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Use as-is</span>
<span class="nc">.masthead</span> <span class="p">{</span>
  <span class="na">background-color</span><span class="o">:</span> <span class="o">@</span><span class="n">brand-primary</span><span class="p">;</span>
<span class="p">}</span>

<span class="c1">// Reassigned variables in Less</span>
<span class="k">@alert-message-background</span><span class="nd">:</span> <span class="o">@</span><span class="nt">brand-info</span><span class="o">;</span>
<span class="nc">.alert</span> <span class="p">{</span>
  <span class="na">background-color</span><span class="o">:</span> <span class="o">@</span><span class="n">alert-message-background</span><span class="p">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-variables-scaffolding">Scaffolding</h3>
                <p>A handful of variables for quickly customizing key elements of your site's skeleton.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Scaffolding</span>
<span class="k">@body-bg</span><span class="nd">:</span>    <span class="nn">#fff</span><span class="o">;</span>
<span class="o">@</span><span class="nt">text-color</span><span class="nd">:</span> <span class="o">@</span><span class="nt">black-50</span><span class="o">;</span>
</code></pre></div>

                <h3 id="less-variables-links">Links</h3>
                <p>Easily style your links with the right color with only one value.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Variables</span>
<span class="k">@link-color</span><span class="nd">:</span>       <span class="o">@</span><span class="nt">brand-primary</span><span class="o">;</span>
<span class="o">@</span><span class="nt">link-hover-color</span><span class="nd">:</span> <span class="nt">darken</span><span class="o">(@</span><span class="nt">link-color</span><span class="o">,</span> <span class="nt">15</span><span class="err">%</span><span class="o">);</span>

<span class="o">//</span> <span class="nt">Usage</span>
<span class="nt">a</span> <span class="p">{</span>
  <span class="na">color</span><span class="o">:</span> <span class="o">@</span><span class="n">link-color</span><span class="p">;</span>
  <span class="na">text-decoration</span><span class="o">:</span> <span class="no">none</span><span class="p">;</span>

  <span class="k">&amp;</span><span class="nd">:hover</span> <span class="p">{</span>
    <span class="na">color</span><span class="o">:</span> <span class="o">@</span><span class="n">link-hover-color</span><span class="p">;</span>
    <span class="na">text-decoration</span><span class="o">:</span> <span class="no">underline</span><span class="p">;</span>
  <span class="p">}</span>
<span class="p">}</span>
</code></pre></div>
                <p>Note that the <code>@link-hover-color</code> uses a function, another awesome tool from Less, to automagically create the right hover color. You can use <code>darken</code>, <code>lighten</code>, <code>saturate</code>, and <code>desaturate</code>.</p>

                <h3 id="less-variables-typography">Typography</h3>
                <p>Easily set your type face, text size, leading, and more with a few quick variables. Bootstrap makes use of these as well to provide easy typographic mixins.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="k">@font-family-sans-serif</span><span class="nd">:</span>  <span class="s2">"Helvetica Neue"</span><span class="o">,</span> <span class="nt">Helvetica</span><span class="o">,</span> <span class="nt">Arial</span><span class="o">,</span> <span class="nt">sans-serif</span><span class="o">;</span>
<span class="o">@</span><span class="nt">font-family-serif</span><span class="nd">:</span>       <span class="nt">Georgia</span><span class="o">,</span> <span class="s2">"Times New Roman"</span><span class="o">,</span> <span class="nt">Times</span><span class="o">,</span> <span class="nt">serif</span><span class="o">;</span>
<span class="o">@</span><span class="nt">font-family-monospace</span><span class="nd">:</span>   <span class="nt">Menlo</span><span class="o">,</span> <span class="nt">Monaco</span><span class="o">,</span> <span class="nt">Consolas</span><span class="o">,</span> <span class="s2">"Courier New"</span><span class="o">,</span> <span class="nt">monospace</span><span class="o">;</span>
<span class="o">@</span><span class="nt">font-family-base</span><span class="nd">:</span>        <span class="o">@</span><span class="nt">font-family-sans-serif</span><span class="o">;</span>

<span class="o">@</span><span class="nt">font-size-base</span><span class="nd">:</span>          <span class="nt">14px</span><span class="o">;</span>
<span class="o">@</span><span class="nt">font-size-large</span><span class="nd">:</span>         <span class="nt">ceil</span><span class="o">((@</span><span class="nt">font-size-base</span> <span class="o">*</span> <span class="nt">1</span><span class="nc">.25</span><span class="o">));</span> <span class="o">//</span> <span class="o">~</span><span class="nt">18px</span>
<span class="o">@</span><span class="nt">font-size-small</span><span class="nd">:</span>         <span class="nt">ceil</span><span class="o">((@</span><span class="nt">font-size-base</span> <span class="o">*</span> <span class="nt">0</span><span class="nc">.85</span><span class="o">));</span> <span class="o">//</span> <span class="o">~</span><span class="nt">12px</span>

<span class="o">@</span><span class="nt">font-size-h1</span><span class="nd">:</span>            <span class="nt">floor</span><span class="o">((@</span><span class="nt">font-size-base</span> <span class="o">*</span> <span class="nt">2</span><span class="nc">.6</span><span class="o">));</span> <span class="o">//</span> <span class="o">~</span><span class="nt">36px</span>
<span class="o">@</span><span class="nt">font-size-h2</span><span class="nd">:</span>            <span class="nt">floor</span><span class="o">((@</span><span class="nt">font-size-base</span> <span class="o">*</span> <span class="nt">2</span><span class="nc">.15</span><span class="o">));</span> <span class="o">//</span> <span class="o">~</span><span class="nt">30px</span>
<span class="o">@</span><span class="nt">font-size-h3</span><span class="nd">:</span>            <span class="nt">ceil</span><span class="o">((@</span><span class="nt">font-size-base</span> <span class="o">*</span> <span class="nt">1</span><span class="nc">.7</span><span class="o">));</span> <span class="o">//</span> <span class="o">~</span><span class="nt">24px</span>
<span class="o">@</span><span class="nt">font-size-h4</span><span class="nd">:</span>            <span class="nt">ceil</span><span class="o">((@</span><span class="nt">font-size-base</span> <span class="o">*</span> <span class="nt">1</span><span class="nc">.25</span><span class="o">));</span> <span class="o">//</span> <span class="o">~</span><span class="nt">18px</span>
<span class="o">@</span><span class="nt">font-size-h5</span><span class="nd">:</span>            <span class="o">@</span><span class="nt">font-size-base</span><span class="o">;</span>
<span class="o">@</span><span class="nt">font-size-h6</span><span class="nd">:</span>            <span class="nt">ceil</span><span class="o">((@</span><span class="nt">font-size-base</span> <span class="o">*</span> <span class="nt">0</span><span class="nc">.85</span><span class="o">));</span> <span class="o">//</span> <span class="o">~</span><span class="nt">12px</span>

<span class="o">@</span><span class="nt">line-height-base</span><span class="nd">:</span>        <span class="nt">1</span><span class="nc">.428571429</span><span class="o">;</span> <span class="o">//</span> <span class="nt">20</span><span class="o">/</span><span class="nt">14</span>
<span class="o">@</span><span class="nt">line-height-computed</span><span class="nd">:</span>    <span class="nt">floor</span><span class="o">((@</span><span class="nt">font-size-base</span> <span class="o">*</span> <span class="o">@</span><span class="nt">line-height-base</span><span class="o">));</span> <span class="o">//</span> <span class="o">~</span><span class="nt">20px</span>

<span class="o">@</span><span class="nt">headings-font-family</span><span class="nd">:</span>    <span class="nt">inherit</span><span class="o">;</span>
<span class="o">@</span><span class="nt">headings-font-weight</span><span class="nd">:</span>    <span class="nt">500</span><span class="o">;</span>
<span class="o">@</span><span class="nt">headings-line-height</span><span class="nd">:</span>    <span class="nt">1</span><span class="nc">.1</span><span class="o">;</span>
<span class="o">@</span><span class="nt">headings-color</span><span class="nd">:</span>          <span class="nt">inherit</span><span class="o">;</span>
</code></pre></div>

                <h3 id="less-variables-icons">Icons</h3>
                <p>Two quick variables for customizing the location and filename of your icons.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="k">@icon-font-path</span><span class="nd">:</span>          <span class="s2">"../fonts/"</span><span class="o">;</span>
<span class="o">@</span><span class="nt">icon-font-name</span><span class="nd">:</span>          <span class="s2">"glyphicons-halflings-regular"</span><span class="o">;</span>
</code></pre></div>

                <h3 id="less-variables-components">Components</h3>
                <p>Components throughout Bootstrap make use of some default variables for setting common values. Here are the most commonly used.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="k">@padding-base-vertical</span><span class="nd">:</span>          <span class="nt">6px</span><span class="o">;</span>
<span class="o">@</span><span class="nt">padding-base-horizontal</span><span class="nd">:</span>        <span class="nt">12px</span><span class="o">;</span>

<span class="o">@</span><span class="nt">padding-large-vertical</span><span class="nd">:</span>         <span class="nt">10px</span><span class="o">;</span>
<span class="o">@</span><span class="nt">padding-large-horizontal</span><span class="nd">:</span>       <span class="nt">16px</span><span class="o">;</span>

<span class="o">@</span><span class="nt">padding-small-vertical</span><span class="nd">:</span>         <span class="nt">5px</span><span class="o">;</span>
<span class="o">@</span><span class="nt">padding-small-horizontal</span><span class="nd">:</span>       <span class="nt">10px</span><span class="o">;</span>

<span class="o">@</span><span class="nt">padding-xs-vertical</span><span class="nd">:</span>            <span class="nt">1px</span><span class="o">;</span>
<span class="o">@</span><span class="nt">padding-xs-horizontal</span><span class="nd">:</span>          <span class="nt">5px</span><span class="o">;</span>

<span class="o">@</span><span class="nt">line-height-large</span><span class="nd">:</span>              <span class="nt">1</span><span class="nc">.33</span><span class="o">;</span>
<span class="o">@</span><span class="nt">line-height-small</span><span class="nd">:</span>              <span class="nt">1</span><span class="nc">.5</span><span class="o">;</span>

<span class="o">@</span><span class="nt">border-radius-base</span><span class="nd">:</span>             <span class="nt">4px</span><span class="o">;</span>
<span class="o">@</span><span class="nt">border-radius-large</span><span class="nd">:</span>            <span class="nt">6px</span><span class="o">;</span>
<span class="o">@</span><span class="nt">border-radius-small</span><span class="nd">:</span>            <span class="nt">3px</span><span class="o">;</span>

<span class="o">@</span><span class="nt">component-active-color</span><span class="nd">:</span>         <span class="nn">#fff</span><span class="o">;</span>
<span class="o">@</span><span class="nt">component-active-bg</span><span class="nd">:</span>            <span class="o">@</span><span class="nt">brand-primary</span><span class="o">;</span>

<span class="o">@</span><span class="nt">caret-width-base</span><span class="nd">:</span>               <span class="nt">4px</span><span class="o">;</span>
<span class="o">@</span><span class="nt">caret-width-large</span><span class="nd">:</span>              <span class="nt">5px</span><span class="o">;</span>
</code></pre></div>


                <h2 id="less-mixins-vendor">Vendor mixins</h2>
                <p>Vendor mixins are mixins to help support multiple browsers by including all relevant vendor prefixes in your compiled CSS.</p>


                <h3 id="less-mixins-box-sizing">Box-sizing</h3>
                <p>Reset your components' box model with a single mixin. For context, see this <a href="https://developer.mozilla.org/en-US/docs/CSS/box-sizing" target="_blank">helpful article from Mozilla</a>.</p>
                <p>The mixin is <strong>deprecated</strong> as of v3.2.0, with the introduction of autoprefixer. To preserve backwards-compatibility, Bootstrap will continue to use the mixin internally until Bootstrap v4.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nc">.box-sizing</span><span class="o">(@</span><span class="nt">box-model</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-box-sizing</span><span class="o">:</span> <span class="o">@</span><span class="n">box-model</span><span class="p">;</span> <span class="c1">// Safari &lt;= 5</span>
     <span class="na">-moz-box-sizing</span><span class="o">:</span> <span class="o">@</span><span class="n">box-model</span><span class="p">;</span> <span class="c1">// Firefox &lt;= 19</span>
          <span class="na">box-sizing</span><span class="o">:</span> <span class="o">@</span><span class="n">box-model</span><span class="p">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-rounded-corners">Rounded corners</h3>
                <p>Today all modern browsers support the non-prefixed <code>border-radius</code> property. As such, there is no <code>.border-radius()</code> mixin, but Bootstrap does include shortcuts for quickly rounding two corners on a particular side of an object.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nc">.border-top-radius</span><span class="o">(@</span><span class="nt">radius</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">border-top-right-radius</span><span class="o">:</span> <span class="o">@</span><span class="n">radius</span><span class="p">;</span>
   <span class="na">border-top-left-radius</span><span class="o">:</span> <span class="o">@</span><span class="n">radius</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.border-right-radius</span><span class="o">(@</span><span class="nt">radius</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">border-bottom-right-radius</span><span class="o">:</span> <span class="o">@</span><span class="n">radius</span><span class="p">;</span>
     <span class="na">border-top-right-radius</span><span class="o">:</span> <span class="o">@</span><span class="n">radius</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.border-bottom-radius</span><span class="o">(@</span><span class="nt">radius</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">border-bottom-right-radius</span><span class="o">:</span> <span class="o">@</span><span class="n">radius</span><span class="p">;</span>
   <span class="na">border-bottom-left-radius</span><span class="o">:</span> <span class="o">@</span><span class="n">radius</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.border-left-radius</span><span class="o">(@</span><span class="nt">radius</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">border-bottom-left-radius</span><span class="o">:</span> <span class="o">@</span><span class="n">radius</span><span class="p">;</span>
     <span class="na">border-top-left-radius</span><span class="o">:</span> <span class="o">@</span><span class="n">radius</span><span class="p">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-box-shadow">Box (Drop) shadows</h3>
                <p>If your target audience is using the latest and greatest browsers and devices, be sure to just use the <code>box-shadow</code> property on its own. If you need support for older Android (pre-v4) and iOS devices (pre-iOS 5), use the <strong>deprecated</strong> mixin to pick up the required <code>-webkit</code> prefix.</p>
                <p>The mixin is <strong>deprecated</strong> as of v3.1.0, since Bootstrap doesn't officially support the outdated platforms that don't support the standard property. To preserve backwards-compatibility, Bootstrap will continue to use the mixin internally until Bootstrap v4.</p>
                <p>Be sure to use <code>rgba()</code> colors in your box shadows so they blend as seamlessly as possible with backgrounds.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="na">.box-shadow(@shadow</span><span class="o">:</span> <span class="mi">0</span> <span class="mi">1</span><span class="kt">px</span> <span class="mi">3</span><span class="kt">px</span> <span class="nf">rgba</span><span class="p">(</span><span class="mi">0</span><span class="o">,</span><span class="mi">0</span><span class="o">,</span><span class="mi">0</span><span class="o">,.</span><span class="mi">25</span><span class="p">))</span> <span class="p">{</span>
  <span class="na">-webkit-box-shadow</span><span class="o">:</span> <span class="o">@</span><span class="n">shadow</span><span class="p">;</span> <span class="c1">// iOS &lt;4.3 &amp; Android &lt;4.1</span>
          <span class="na">box-shadow</span><span class="o">:</span> <span class="o">@</span><span class="n">shadow</span><span class="p">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-transitions">Transitions</h3>
                <p>Multiple mixins for flexibility. Set all transition information with one, or specify a separate delay and duration as needed.</p>
                <p>The mixins are <strong>deprecated</strong> as of v3.2.0, with the introduction of autoprefixer. To preserve backwards-compatibility, Bootstrap will continue to use the mixins internally until Bootstrap v4.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nc">.transition</span><span class="o">(@</span><span class="nt">transition</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transition</span><span class="o">:</span> <span class="o">@</span><span class="n">transition</span><span class="p">;</span>
          <span class="na">transition</span><span class="o">:</span> <span class="o">@</span><span class="n">transition</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.transition-property</span><span class="o">(@</span><span class="nt">transition-property</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transition-property</span><span class="o">:</span> <span class="o">@</span><span class="n">transition-property</span><span class="p">;</span>
          <span class="na">transition-property</span><span class="o">:</span> <span class="o">@</span><span class="n">transition-property</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.transition-delay</span><span class="o">(@</span><span class="nt">transition-delay</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transition-delay</span><span class="o">:</span> <span class="o">@</span><span class="n">transition-delay</span><span class="p">;</span>
          <span class="na">transition-delay</span><span class="o">:</span> <span class="o">@</span><span class="n">transition-delay</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.transition-duration</span><span class="o">(@</span><span class="nt">transition-duration</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transition-duration</span><span class="o">:</span> <span class="o">@</span><span class="n">transition-duration</span><span class="p">;</span>
          <span class="na">transition-duration</span><span class="o">:</span> <span class="o">@</span><span class="n">transition-duration</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.transition-timing-function</span><span class="o">(@</span><span class="nt">timing-function</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transition-timing-function</span><span class="o">:</span> <span class="o">@</span><span class="n">timing-function</span><span class="p">;</span>
          <span class="na">transition-timing-function</span><span class="o">:</span> <span class="o">@</span><span class="n">timing-function</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.transition-transform</span><span class="o">(@</span><span class="nt">transition</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transition</span><span class="o">:</span> <span class="o">-</span><span class="n">webkit-transform</span> <span class="o">@</span><span class="n">transition</span><span class="p">;</span>
     <span class="na">-moz-transition</span><span class="o">:</span> <span class="o">-</span><span class="n">moz-transform</span> <span class="o">@</span><span class="n">transition</span><span class="p">;</span>
       <span class="na">-o-transition</span><span class="o">:</span> <span class="o">-</span><span class="n">o-transform</span> <span class="o">@</span><span class="n">transition</span><span class="p">;</span>
          <span class="na">transition</span><span class="o">:</span> <span class="n">transform</span> <span class="o">@</span><span class="n">transition</span><span class="p">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-transformations">Transformations</h3>
                <p>Rotate, scale, translate (move), or skew any object.</p>
                <p>The mixins are <strong>deprecated</strong> as of v3.2.0, with the introduction of autoprefixer. To preserve backwards-compatibility, Bootstrap will continue to use the mixins internally until Bootstrap v4.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nc">.rotate</span><span class="o">(@</span><span class="nt">degrees</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transform</span><span class="o">:</span> <span class="nf">rotate</span><span class="p">(</span><span class="o">@</span><span class="n">degrees</span><span class="p">);</span>
      <span class="na">-ms-transform</span><span class="o">:</span> <span class="nf">rotate</span><span class="p">(</span><span class="o">@</span><span class="n">degrees</span><span class="p">);</span> <span class="c1">// IE9 only</span>
          <span class="na">transform</span><span class="o">:</span> <span class="nf">rotate</span><span class="p">(</span><span class="o">@</span><span class="n">degrees</span><span class="p">);</span>
<span class="p">}</span>
<span class="nc">.scale</span><span class="o">(@</span><span class="nt">ratio</span><span class="o">;</span> <span class="o">@</span><span class="nt">ratio-y</span><span class="nc">...</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transform</span><span class="o">:</span> <span class="nf">scale</span><span class="p">(</span><span class="o">@</span><span class="n">ratio</span><span class="o">,</span> <span class="o">@</span><span class="n">ratio-y</span><span class="p">);</span>
      <span class="na">-ms-transform</span><span class="o">:</span> <span class="nf">scale</span><span class="p">(</span><span class="o">@</span><span class="n">ratio</span><span class="o">,</span> <span class="o">@</span><span class="n">ratio-y</span><span class="p">);</span> <span class="c1">// IE9 only</span>
          <span class="na">transform</span><span class="o">:</span> <span class="nf">scale</span><span class="p">(</span><span class="o">@</span><span class="n">ratio</span><span class="o">,</span> <span class="o">@</span><span class="n">ratio-y</span><span class="p">);</span>
<span class="p">}</span>
<span class="nc">.translate</span><span class="o">(@</span><span class="nt">x</span><span class="o">;</span> <span class="o">@</span><span class="nt">y</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transform</span><span class="o">:</span> <span class="nf">translate</span><span class="p">(</span><span class="o">@</span><span class="n">x</span><span class="o">,</span> <span class="o">@</span><span class="n">y</span><span class="p">);</span>
      <span class="na">-ms-transform</span><span class="o">:</span> <span class="nf">translate</span><span class="p">(</span><span class="o">@</span><span class="n">x</span><span class="o">,</span> <span class="o">@</span><span class="n">y</span><span class="p">);</span> <span class="c1">// IE9 only</span>
          <span class="na">transform</span><span class="o">:</span> <span class="nf">translate</span><span class="p">(</span><span class="o">@</span><span class="n">x</span><span class="o">,</span> <span class="o">@</span><span class="n">y</span><span class="p">);</span>
<span class="p">}</span>
<span class="nc">.skew</span><span class="o">(@</span><span class="nt">x</span><span class="o">;</span> <span class="o">@</span><span class="nt">y</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transform</span><span class="o">:</span> <span class="nf">skew</span><span class="p">(</span><span class="o">@</span><span class="n">x</span><span class="o">,</span> <span class="o">@</span><span class="n">y</span><span class="p">);</span>
      <span class="na">-ms-transform</span><span class="o">:</span> <span class="nf">skewX</span><span class="p">(</span><span class="o">@</span><span class="n">x</span><span class="p">)</span> <span class="nf">skewY</span><span class="p">(</span><span class="o">@</span><span class="n">y</span><span class="p">);</span> <span class="c1">// See https://github.com/twbs/bootstrap/issues/4885; IE9+</span>
          <span class="na">transform</span><span class="o">:</span> <span class="nf">skew</span><span class="p">(</span><span class="o">@</span><span class="n">x</span><span class="o">,</span> <span class="o">@</span><span class="n">y</span><span class="p">);</span>
<span class="p">}</span>
<span class="nc">.translate3d</span><span class="o">(@</span><span class="nt">x</span><span class="o">;</span> <span class="o">@</span><span class="nt">y</span><span class="o">;</span> <span class="o">@</span><span class="nt">z</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transform</span><span class="o">:</span> <span class="nf">translate3d</span><span class="p">(</span><span class="o">@</span><span class="n">x</span><span class="o">,</span> <span class="o">@</span><span class="n">y</span><span class="o">,</span> <span class="o">@</span><span class="n">z</span><span class="p">);</span>
          <span class="na">transform</span><span class="o">:</span> <span class="nf">translate3d</span><span class="p">(</span><span class="o">@</span><span class="n">x</span><span class="o">,</span> <span class="o">@</span><span class="n">y</span><span class="o">,</span> <span class="o">@</span><span class="n">z</span><span class="p">);</span>
<span class="p">}</span>

<span class="nc">.rotateX</span><span class="o">(@</span><span class="nt">degrees</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transform</span><span class="o">:</span> <span class="nf">rotateX</span><span class="p">(</span><span class="o">@</span><span class="n">degrees</span><span class="p">);</span>
      <span class="na">-ms-transform</span><span class="o">:</span> <span class="nf">rotateX</span><span class="p">(</span><span class="o">@</span><span class="n">degrees</span><span class="p">);</span> <span class="c1">// IE9 only</span>
          <span class="na">transform</span><span class="o">:</span> <span class="nf">rotateX</span><span class="p">(</span><span class="o">@</span><span class="n">degrees</span><span class="p">);</span>
<span class="p">}</span>
<span class="nc">.rotateY</span><span class="o">(@</span><span class="nt">degrees</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transform</span><span class="o">:</span> <span class="nf">rotateY</span><span class="p">(</span><span class="o">@</span><span class="n">degrees</span><span class="p">);</span>
      <span class="na">-ms-transform</span><span class="o">:</span> <span class="nf">rotateY</span><span class="p">(</span><span class="o">@</span><span class="n">degrees</span><span class="p">);</span> <span class="c1">// IE9 only</span>
          <span class="na">transform</span><span class="o">:</span> <span class="nf">rotateY</span><span class="p">(</span><span class="o">@</span><span class="n">degrees</span><span class="p">);</span>
<span class="p">}</span>
<span class="nc">.perspective</span><span class="o">(@</span><span class="nt">perspective</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-perspective</span><span class="o">:</span> <span class="o">@</span><span class="n">perspective</span><span class="p">;</span>
     <span class="na">-moz-perspective</span><span class="o">:</span> <span class="o">@</span><span class="n">perspective</span><span class="p">;</span>
          <span class="na">perspective</span><span class="o">:</span> <span class="o">@</span><span class="n">perspective</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.perspective-origin</span><span class="o">(@</span><span class="nt">perspective</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-perspective-origin</span><span class="o">:</span> <span class="o">@</span><span class="n">perspective</span><span class="p">;</span>
     <span class="na">-moz-perspective-origin</span><span class="o">:</span> <span class="o">@</span><span class="n">perspective</span><span class="p">;</span>
          <span class="na">perspective-origin</span><span class="o">:</span> <span class="o">@</span><span class="n">perspective</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.transform-origin</span><span class="o">(@</span><span class="nt">origin</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-transform-origin</span><span class="o">:</span> <span class="o">@</span><span class="ow">or</span><span class="n">igin</span><span class="p">;</span>
     <span class="na">-moz-transform-origin</span><span class="o">:</span> <span class="o">@</span><span class="ow">or</span><span class="n">igin</span><span class="p">;</span>
      <span class="na">-ms-transform-origin</span><span class="o">:</span> <span class="o">@</span><span class="ow">or</span><span class="n">igin</span><span class="p">;</span> <span class="c1">// IE9 only</span>
          <span class="na">transform-origin</span><span class="o">:</span> <span class="o">@</span><span class="ow">or</span><span class="n">igin</span><span class="p">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-animations">Animations</h3>
                <p>A single mixin for using all of CSS3's animation properties in one declaration and other mixins for individual properties.</p>
                <p>The mixins are <strong>deprecated</strong> as of v3.2.0, with the introduction of autoprefixer. To preserve backwards-compatibility, Bootstrap will continue to use the mixins internally until Bootstrap v4.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nc">.animation</span><span class="o">(@</span><span class="nt">animation</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-animation</span><span class="o">:</span> <span class="o">@</span><span class="n">animation</span><span class="p">;</span>
          <span class="na">animation</span><span class="o">:</span> <span class="o">@</span><span class="n">animation</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.animation-name</span><span class="o">(@</span><span class="nt">name</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-animation-name</span><span class="o">:</span> <span class="o">@</span><span class="n">name</span><span class="p">;</span>
          <span class="na">animation-name</span><span class="o">:</span> <span class="o">@</span><span class="n">name</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.animation-duration</span><span class="o">(@</span><span class="nt">duration</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-animation-duration</span><span class="o">:</span> <span class="o">@</span><span class="n">duration</span><span class="p">;</span>
          <span class="na">animation-duration</span><span class="o">:</span> <span class="o">@</span><span class="n">duration</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.animation-timing-function</span><span class="o">(@</span><span class="nt">timing-function</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-animation-timing-function</span><span class="o">:</span> <span class="o">@</span><span class="n">timing-function</span><span class="p">;</span>
          <span class="na">animation-timing-function</span><span class="o">:</span> <span class="o">@</span><span class="n">timing-function</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.animation-delay</span><span class="o">(@</span><span class="nt">delay</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-animation-delay</span><span class="o">:</span> <span class="o">@</span><span class="n">delay</span><span class="p">;</span>
          <span class="na">animation-delay</span><span class="o">:</span> <span class="o">@</span><span class="n">delay</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.animation-iteration-count</span><span class="o">(@</span><span class="nt">iteration-count</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-animation-iteration-count</span><span class="o">:</span> <span class="o">@</span><span class="n">iteration-count</span><span class="p">;</span>
          <span class="na">animation-iteration-count</span><span class="o">:</span> <span class="o">@</span><span class="n">iteration-count</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.animation-direction</span><span class="o">(@</span><span class="nt">direction</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-animation-direction</span><span class="o">:</span> <span class="o">@</span><span class="no">direction</span><span class="p">;</span>
          <span class="na">animation-direction</span><span class="o">:</span> <span class="o">@</span><span class="no">direction</span><span class="p">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-opacity">Opacity</h3>
                <p>Set the opacity for all browsers and provide a <code>filter</code> fallback for IE8.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nc">.opacity</span><span class="o">(@</span><span class="nt">opacity</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">opacity</span><span class="o">:</span> <span class="o">@</span><span class="no">opacity</span><span class="p">;</span>
  <span class="c1">// IE8 filter</span>
  <span class="k">@opacity-ie</span><span class="nd">:</span> <span class="o">(@</span><span class="nt">opacity</span> <span class="o">*</span> <span class="nt">100</span><span class="o">);</span>
  <span class="nt">filter</span><span class="nd">:</span> <span class="o">~</span><span class="s2">"alpha(opacity=@{opacity-ie})"</span><span class="o">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-placeholder">Placeholder text</h3>
                <p>Provide context for form controls within each field.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="na">.placeholder(@color</span><span class="o">:</span> <span class="o">@</span><span class="n">input-color-placeholder</span><span class="p">)</span> <span class="p">{</span>
  <span class="na">&amp;</span><span class="o">::-</span><span class="n">moz-placeholder</span>           <span class="p">{</span> <span class="na">color</span><span class="o">:</span> <span class="o">@</span><span class="no">color</span><span class="p">;</span> <span class="p">}</span> <span class="c1">// Firefox</span>
  <span class="na">&amp;</span><span class="o">:-</span><span class="n">ms-input-placeholder</span>       <span class="p">{</span> <span class="na">color</span><span class="o">:</span> <span class="o">@</span><span class="no">color</span><span class="p">;</span> <span class="p">}</span> <span class="c1">// Internet Explorer 10+</span>
  <span class="na">&amp;</span><span class="o">::-</span><span class="n">webkit-input-placeholder</span>  <span class="p">{</span> <span class="na">color</span><span class="o">:</span> <span class="o">@</span><span class="no">color</span><span class="p">;</span> <span class="p">}</span> <span class="c1">// Safari and Chrome</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-columns">Columns</h3>
                <p>Generate columns via CSS within a single element.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nc">.content-columns</span><span class="o">(@</span><span class="nt">width</span><span class="o">;</span> <span class="o">@</span><span class="nt">count</span><span class="o">;</span> <span class="o">@</span><span class="nt">gap</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">-webkit-column-width</span><span class="o">:</span> <span class="o">@</span><span class="no">width</span><span class="p">;</span>
     <span class="na">-moz-column-width</span><span class="o">:</span> <span class="o">@</span><span class="no">width</span><span class="p">;</span>
          <span class="na">column-width</span><span class="o">:</span> <span class="o">@</span><span class="no">width</span><span class="p">;</span>
  <span class="na">-webkit-column-count</span><span class="o">:</span> <span class="o">@</span><span class="n">count</span><span class="p">;</span>
     <span class="na">-moz-column-count</span><span class="o">:</span> <span class="o">@</span><span class="n">count</span><span class="p">;</span>
          <span class="na">column-count</span><span class="o">:</span> <span class="o">@</span><span class="n">count</span><span class="p">;</span>
  <span class="na">-webkit-column-gap</span><span class="o">:</span> <span class="o">@</span><span class="n">gap</span><span class="p">;</span>
     <span class="na">-moz-column-gap</span><span class="o">:</span> <span class="o">@</span><span class="n">gap</span><span class="p">;</span>
          <span class="na">column-gap</span><span class="o">:</span> <span class="o">@</span><span class="n">gap</span><span class="p">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-gradients">Gradients</h3>
                <p>Easily turn any two colors into a background gradient. Get more advanced and set a direction, use three colors, or use a radial gradient. With a single mixin you get all the prefixed syntaxes you'll need.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nn">#gradient</span> <span class="o">&gt;</span> <span class="nc">.vertical</span><span class="o">(</span><span class="nn">#333</span><span class="o">;</span> <span class="nn">#000</span><span class="o">);</span>
<span class="nn">#gradient</span> <span class="o">&gt;</span> <span class="nc">.horizontal</span><span class="o">(</span><span class="nn">#333</span><span class="o">;</span> <span class="nn">#000</span><span class="o">);</span>
<span class="nn">#gradient</span> <span class="o">&gt;</span> <span class="nc">.radial</span><span class="o">(</span><span class="nn">#333</span><span class="o">;</span> <span class="nn">#000</span><span class="o">);</span>
</code></pre></div>
                <p>You can also specify the angle of a standard two-color, linear gradient:</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nn">#gradient</span> <span class="o">&gt;</span> <span class="nc">.directional</span><span class="o">(</span><span class="nn">#333</span><span class="o">;</span> <span class="nn">#000</span><span class="o">;</span> <span class="nt">45deg</span><span class="o">);</span>
</code></pre></div>
                <p>If you need a barber-stripe style gradient, that's easy, too. Just specify a single color and we'll overlay a translucent white stripe.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nn">#gradient</span> <span class="o">&gt;</span> <span class="nc">.striped</span><span class="o">(</span><span class="nn">#333</span><span class="o">;</span> <span class="nt">45deg</span><span class="o">);</span>
</code></pre></div>
                <p>Up the ante and use three colors instead. Set the first color, the second color, the second color's color stop (a percentage value like 25%), and the third color with these mixins:</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nn">#gradient</span> <span class="o">&gt;</span> <span class="nc">.vertical-three-colors</span><span class="o">(</span><span class="nn">#777</span><span class="o">;</span> <span class="nn">#333</span><span class="o">;</span> <span class="nt">25</span><span class="err">%</span><span class="o">;</span> <span class="nn">#000</span><span class="o">);</span>
<span class="nn">#gradient</span> <span class="o">&gt;</span> <span class="nc">.horizontal-three-colors</span><span class="o">(</span><span class="nn">#777</span><span class="o">;</span> <span class="nn">#333</span><span class="o">;</span> <span class="nt">25</span><span class="err">%</span><span class="o">;</span> <span class="nn">#000</span><span class="o">);</span>
</code></pre></div>
                <p><strong>Heads up!</strong> Should you ever need to remove a gradient, be sure to remove any IE-specific <code>filter</code> you may have added. You can do that by using the <code>.reset-filter()</code> mixin alongside <code>background-image: none;</code>.</p>


                <h2 id="less-mixins-utility">Utility mixins</h2>
                <p>Utility mixins are mixins that combine otherwise unrelated CSS properties to achieve a specific goal or task.</p>

                <h3 id="less-mixins-clearfix">Clearfix</h3>
                <p>Forget adding <code>class="clearfix"</code> to any element and instead add the <code>.clearfix()</code> mixin where appropriate. Uses the <a href="http://nicolasgallagher.com/micro-clearfix-hack/" target="_blank">micro clearfix</a> from <a href="https://twitter.com/necolas" target="_blank">Nicolas Gallager</a>.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Mixin</span>
<span class="nc">.clearfix</span><span class="o">()</span> <span class="p">{</span>
  <span class="k">&amp;</span><span class="nd">:before</span><span class="o">,</span>
  <span class="k">&amp;</span><span class="nd">:after</span> <span class="p">{</span>
    <span class="na">content</span><span class="o">:</span> <span class="s2">" "</span><span class="p">;</span>
    <span class="na">display</span><span class="o">:</span> <span class="n">table</span><span class="p">;</span>
  <span class="p">}</span>
  <span class="k">&amp;</span><span class="nd">:after</span> <span class="p">{</span>
    <span class="na">clear</span><span class="o">:</span> <span class="no">both</span><span class="p">;</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Usage</span>
<span class="nc">.container</span> <span class="p">{</span>
  <span class="nc">.clearfix</span><span class="o">();</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-centering">Horizontal centering</h3>
                <p>Quickly center any element within its parent. <strong>Requires <code>width</code> or <code>max-width</code> to be set.</strong></p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Mixin</span>
<span class="nc">.center-block</span><span class="o">()</span> <span class="p">{</span>
  <span class="na">display</span><span class="o">:</span> <span class="no">block</span><span class="p">;</span>
  <span class="na">margin-left</span><span class="o">:</span> <span class="no">auto</span><span class="p">;</span>
  <span class="na">margin-right</span><span class="o">:</span> <span class="no">auto</span><span class="p">;</span>
<span class="p">}</span>

<span class="c1">// Usage</span>
<span class="nc">.container</span> <span class="p">{</span>
  <span class="na">width</span><span class="o">:</span> <span class="mi">940</span><span class="kt">px</span><span class="p">;</span>
  <span class="nc">.center-block</span><span class="o">();</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-sizing">Sizing helpers</h3>
                <p>Specify the dimensions of an object more easily.</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Mixins</span>
<span class="nc">.size</span><span class="o">(@</span><span class="nt">width</span><span class="o">;</span> <span class="o">@</span><span class="nt">height</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">width</span><span class="o">:</span> <span class="o">@</span><span class="no">width</span><span class="p">;</span>
  <span class="na">height</span><span class="o">:</span> <span class="o">@</span><span class="no">height</span><span class="p">;</span>
<span class="p">}</span>
<span class="nc">.square</span><span class="o">(@</span><span class="nt">size</span><span class="o">)</span> <span class="p">{</span>
  <span class="nc">.size</span><span class="o">(@</span><span class="nt">size</span><span class="o">;</span> <span class="o">@</span><span class="nt">size</span><span class="o">);</span>
<span class="p">}</span>

<span class="c1">// Usage</span>
<span class="nc">.image</span> <span class="p">{</span> <span class="nc">.size</span><span class="o">(</span><span class="nt">400px</span><span class="o">;</span> <span class="nt">300px</span><span class="o">);</span> <span class="p">}</span>
<span class="nc">.avatar</span> <span class="p">{</span> <span class="nc">.square</span><span class="o">(</span><span class="nt">48px</span><span class="o">);</span> <span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-resizable">Resizable textareas</h3>
                <p>Easily configure the resize options for any textarea, or any other element. Defaults to normal browser behavior (<code>both</code>).</p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="na">.resizable(@direction</span><span class="o">:</span> <span class="no">both</span><span class="p">)</span> <span class="p">{</span>
  <span class="c1">// Options: horizontal, vertical, both</span>
  <span class="na">resize</span><span class="o">:</span> <span class="o">@</span><span class="no">direction</span><span class="p">;</span>
  <span class="c1">// Safari fix</span>
  <span class="na">overflow</span><span class="o">:</span> <span class="no">auto</span><span class="p">;</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-truncating">Truncating text</h3>
                <p>Easily truncate text with an ellipsis with a single mixin. <strong>Requires element to be <code>block</code> or <code>inline-block</code> level.</strong></p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="c1">// Mixin</span>
<span class="nc">.text-overflow</span><span class="o">()</span> <span class="p">{</span>
  <span class="na">overflow</span><span class="o">:</span> <span class="no">hidden</span><span class="p">;</span>
  <span class="na">text-overflow</span><span class="o">:</span> <span class="n">ellipsis</span><span class="p">;</span>
  <span class="na">white-space</span><span class="o">:</span> <span class="no">nowrap</span><span class="p">;</span>
<span class="p">}</span>

<span class="c1">// Usage</span>
<span class="nc">.branch-name</span> <span class="p">{</span>
  <span class="na">display</span><span class="o">:</span> <span class="no">inline</span><span class="o">-</span><span class="no">block</span><span class="p">;</span>
  <span class="na">max-width</span><span class="o">:</span> <span class="mi">200</span><span class="kt">px</span><span class="p">;</span>
  <span class="nc">.text-overflow</span><span class="o">();</span>
<span class="p">}</span>
</code></pre></div>

                <h3 id="less-mixins-retina-images">Retina images</h3>
                <p>Specify two image paths and the @1x image dimensions, and Bootstrap will provide an @2x media query. <strong>If you have many images to serve, consider writing your retina image CSS manually in a single media query.</strong></p>
                <div class="zero-clipboard"><span class="btn-clipboard">Copy</span></div><div class="highlight"><pre><code class="scss"><span class="nc">.img-retina</span><span class="o">(@</span><span class="nt">file-1x</span><span class="o">;</span> <span class="o">@</span><span class="nt">file-2x</span><span class="o">;</span> <span class="o">@</span><span class="nt">width-1x</span><span class="o">;</span> <span class="o">@</span><span class="nt">height-1x</span><span class="o">)</span> <span class="p">{</span>
  <span class="na">background-image</span><span class="o">:</span> <span class="sx">url("@{file-1x}")</span><span class="p">;</span>

  <span class="k">@media</span>
  <span class="nt">only</span> <span class="nt">screen</span> <span class="nt">and</span> <span class="o">(</span><span class="nt">-webkit-min-device-pixel-ratio</span><span class="nd">:</span> <span class="nt">2</span><span class="o">),</span>
  <span class="nt">only</span> <span class="nt">screen</span> <span class="nt">and</span> <span class="o">(</span>   <span class="nt">min--moz-device-pixel-ratio</span><span class="nd">:</span> <span class="nt">2</span><span class="o">),</span>
  <span class="nt">only</span> <span class="nt">screen</span> <span class="nt">and</span> <span class="o">(</span>     <span class="nt">-o-min-device-pixel-ratio</span><span class="nd">:</span> <span class="nt">2</span><span class="o">/</span><span class="nt">1</span><span class="o">),</span>
  <span class="nt">only</span> <span class="nt">screen</span> <span class="nt">and</span> <span class="o">(</span>        <span class="nt">min-device-pixel-ratio</span><span class="nd">:</span> <span class="nt">2</span><span class="o">),</span>
  <span class="nt">only</span> <span class="nt">screen</span> <span class="nt">and</span> <span class="o">(</span>                <span class="nt">min-resolution</span><span class="nd">:</span> <span class="nt">192dpi</span><span class="o">),</span>
  <span class="nt">only</span> <span class="nt">screen</span> <span class="nt">and</span> <span class="o">(</span>                <span class="nt">min-resolution</span><span class="nd">:</span> <span class="nt">2dppx</span><span class="o">)</span> <span class="p">{</span>
    <span class="na">background-image</span><span class="o">:</span> <span class="sx">url("@{file-2x}")</span><span class="p">;</span>
    <span class="na">background-size</span><span class="o">:</span> <span class="o">@</span><span class="no">width</span><span class="mi">-1</span><span class="kt">x</span> <span class="o">@</span><span class="no">height</span><span class="mi">-1</span><span class="kt">x</span><span class="p">;</span>
  <span class="p">}</span>
<span class="p">}</span>

<span class="c1">// Usage</span>
<span class="nc">.jumbotron</span> <span class="p">{</span>
  <span class="nc">.img-retina</span><span class="o">(</span><span class="s2">"/img/bg-1x.png"</span><span class="o">,</span> <span class="s2">"/img/bg-2x.png"</span><span class="o">,</span> <span class="nt">100px</span><span class="o">,</span> <span class="nt">100px</span><span class="o">);</span>
<span class="p">}</span>
</code></pre></div>
            </div>

            <div class="bs-docs-section">
                <h1 id="sass" class="page-header">使用 Sass</h1>
                <p class="lead">虽然 Bootstrap 是基于 Less 构建的，我们还提供了一套<a href="https://github.com/twbs/bootstrap-sass">官方支持的 Sass 移植版</a>代码。我们将这个版本放在单独的 GitHub 仓库中进行维护，并通过脚本处理源码更新。</p>

                <h2 id="sass-contents">包含的内容</h2>
                <p>由于 Sass 移植版存放于单独的仓库，并针对不同的使用群体，这个项目中的内容与 Bootstrap 主项目有很大不同。这也是为了保证 Sass 移植版与更多基于 Sass 的系统相兼容。</p>

                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th>路径</th>
                            <th>描述</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th><code>lib/</code></th>
                            <td>Ruby gem code (Sass configuration, Rails and Compass integrations)</td>
                        </tr>
                        <tr>
                            <th><code>tasks/</code></th>
                            <td>Converter scripts (turning upstream Less to Sass)</td>
                        </tr>
                        <tr>
                            <th><code>test/</code></th>
                            <td>Compilation tests</td>
                        </tr>
                        <tr>
                            <th><code>templates/</code></th>
                            <td>Compass package manifest</td>
                        </tr>
                        <tr>
                            <th><code>vendor/assets/</code></th>
                            <td>Sass, JavaScript, and font files</td>
                        </tr>
                        <tr>
                            <th><code>Rakefile</code></th>
                            <td>Internal tasks, such as rake and convert</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <p>请访问 <a href="https://github.com/twbs/bootstrap-sass">Sass 移植版在 GitHub 上的仓库</a> 来了解这些文件。</p>


                <h2 id="sass-installation">安装</h2>
                <p>关于如何安装并使用 Bootstrap 的 Sass 移植版，请参考<a href="https://github.com/twbs/bootstrap-sass">GitHub 仓库中的 readme 文件</a>。 此仓库中包含了最新的源码以及如何与 Rails、Compass 以及标准 Sass 项目一同使用的详细信息。</p>
                <p>
                    <a class="btn btn-lg btn-outline" href="https://github.com/twbs/bootstrap-sass">Bootstrap for Sass</a>
                </p>
            </div>


        </div>
        <div class="col-md-3">
            <div class="bs-docs-sidebar hidden-print hidden-xs hidden-sm affix-top" role="complementary">
                <ul class="nav bs-docs-sidenav">

                    <li>
                        <a href="#overview">概览</a>
                        <ul class="nav">
                            <li><a href="#overview-doctype">HTML5 文档类型</a></li>
                            <li><a href="#overview-mobile">移动设备优先</a></li>
                            <li><a href="#overview-type-links">排版与链接</a></li>
                            <li><a href="#overview-normalize">Normalize.css</a></li>
                            <li><a href="#overview-container">布局容器</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#grid">栅格系统</a>
                        <ul class="nav">
                            <li><a href="#grid-intro">简介</a></li>
                            <li><a href="#grid-media-queries">媒体查询</a></li>
                            <li><a href="#grid-options">栅格参数</a></li>
                            <li><a href="#grid-example-basic">实例：从堆叠到水平排列</a></li>
                            <li><a href="#grid-example-fluid">实例：流式布局容器</a></li>
                            <li><a href="#grid-example-mixed">实例：移动设备和桌面屏幕</a></li>
                            <li><a href="#grid-example-mixed-complete">实例：手机、平板、桌面</a></li>
                            <li><a href="#grid-example-wrapping">实例：多余的列（column）将另起一行排列</a></li>
                            <li><a href="#grid-responsive-resets">Responsive column resets</a></li>
                            <li><a href="#grid-offsetting">列偏移</a></li>
                            <li><a href="#grid-nesting">嵌套列</a></li>
                            <li><a href="#grid-column-ordering">列排序</a></li>
                            <li><a href="#grid-less">Less mixin 和变量</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#type">排版</a>
                        <ul class="nav">
                            <li><a href="#type-headings">标题</a></li>
                            <li><a href="#type-body-copy">页面主体</a></li>
                            <li><a href="#type-inline-text">内联文本元素</a></li>
                            <li><a href="#type-alignment">对齐</a></li>
                            <li><a href="#type-transformation">改变大小写</a></li>
                            <li><a href="#type-abbreviations">缩略语</a></li>
                            <li><a href="#type-addresses">地址</a></li>
                            <li><a href="#type-blockquotes">引用</a></li>
                            <li><a href="#type-lists">列表</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#code">代码</a>
                        <ul class="nav">
                            <li><a href="#code-inline">内联代码</a></li>
                            <li><a href="#code-user-input">用户输入</a></li>
                            <li><a href="#code-block">代码块</a></li>
                            <li><a href="#code-variables">变量</a></li>
                            <li><a href="#code-sample-output">程序输出</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#tables">表格</a>
                        <ul class="nav">
                            <li><a href="#tables-example">基本实例</a></li>
                            <li><a href="#tables-striped">条纹状表格</a></li>
                            <li><a href="#tables-bordered">带边框的表格</a></li>
                            <li><a href="#tables-hover-rows">鼠标悬停</a></li>
                            <li><a href="#tables-condensed">紧缩表格</a></li>
                            <li><a href="#tables-contextual-classes">状态类</a></li>
                            <li><a href="#tables-responsive">响应式表格</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#forms">表单</a>
                        <ul class="nav">
                            <li><a href="#forms-example">基本实例</a></li>
                            <li><a href="#forms-inline">内联表单</a></li>
                            <li><a href="#forms-horizontal">水平排列的表单</a></li>
                            <li><a href="#forms-controls">被支持的控件</a></li>
                            <li><a href="#forms-controls-static">静态控件</a></li>
                            <li><a href="#forms-control-focus">焦点状态</a></li>
                            <li><a href="#forms-control-disabled">禁用状态</a></li>
                            <li><a href="#forms-control-readonly">只读状态</a></li>
                            <li><a href="#forms-control-validation">校验状态</a></li>
                            <li><a href="#forms-control-sizes">控件尺寸</a></li>
                            <li><a href="#forms-help-text">辅助文本</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#buttons">按钮</a>
                        <ul class="nav">
                            <li><a href="#buttons-options">预定义样式</a></li>
                            <li><a href="#buttons-sizes">尺寸</a></li>
                            <li><a href="#buttons-active">激活状态</a></li>
                            <li><a href="#buttons-disabled">禁用状态</a></li>
                            <li><a href="#buttons-tags">按钮类</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#images">图片</a>
                        <ul class="nav">
                            <li><a href="#images-responsive">响应式图片</a></li>
                            <li><a href="#images-shapes">图片形状</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#helper-classes">辅助类</a>
                        <ul class="nav">
                            <li><a href="#helper-classes-colors">Contextual colors</a></li>
                            <li><a href="#helper-classes-backgrounds">Contextual backgrounds</a></li>
                            <li><a href="#helper-classes-close">关闭按钮</a></li>
                            <li><a href="#helper-classes-carets">三角符号</a></li>
                            <li><a href="#helper-classes-floats">快速浮动</a></li>
                            <li><a href="#helper-classes-center">Center content blocks</a></li>
                            <li><a href="#helper-classes-clearfix">Clearfix</a></li>
                            <li><a href="#helper-classes-show-hide">Showing and hiding content</a></li>
                            <li><a href="#helper-classes-screen-readers">Screen reader content</a></li>
                            <li><a href="#helper-classes-image-replacement">Image replacement</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#responsive-utilities">响应式工具</a>
                        <ul class="nav">
                            <li><a href="#responsive-utilities-classes">可用的类</a></li>
                            <li><a href="#responsive-utilities-print">打印类</a></li>
                            <li><a href="#responsive-utilities-tests">测试用例</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#less">使用 Less</a>
                        <ul class="nav">
                            <li><a href="#less-bootstrap">编译 Bootstrap</a></li>
                            <li><a href="#less-variables">变量</a></li>
                            <li><a href="#less-mixins-vendor">针对特定厂商的 mixin</a></li>
                            <li><a href="#less-mixins-utility">工具 mixin</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#sass">使用 Sass</a>
                        <ul class="nav">
                            <li><a href="#sass-contents">包含的内容</a></li>
                            <li><a href="#sass-installation">安装</a></li>
                        </ul>
                    </li>


                </ul>
                <a class="back-to-top" href="#top">
                    返回顶部
                </a>

                <a href="#" class="bs-docs-theme-toggle" role="button">
                    主题预览
                </a>

            </div>
        </div>
    </div>
</div>
<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/require.js"></script>
<script>
    require(["bootstrap", "docs"], function () {
    });
</script>
</body>
</html>
