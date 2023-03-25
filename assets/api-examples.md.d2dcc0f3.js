import{u as o,o as i,c,x as a,t as s,b as t,N as d,a as e}from"./chunks/framework.59794696.js";const h=d(`<h1 id="runtime-api-examples" tabindex="-1">Runtime API Examples <a class="header-anchor" href="#runtime-api-examples" aria-label="Permalink to &quot;Runtime API Examples&quot;">​</a></h1><p>This page demonstrates usage of some of the runtime APIs provided by VitePress.</p><p>The main <code>useData()</code> API can be used to access site, theme, and page data for the current page. It works in both <code>.md</code> and <code>.vue</code> files:</p><div class="language-md"><button title="Copy Code" class="copy"></button><span class="lang">md</span><pre class="shiki material-theme-palenight"><code><span class="line"><span style="color:#A6ACCD;">&lt;script setup&gt;</span></span>
<span class="line"><span style="color:#A6ACCD;">import { useData } from &#39;vitepress&#39;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#A6ACCD;">const { site, theme, page, frontmatter } = useData()</span></span>
<span class="line"><span style="color:#A6ACCD;">&lt;/script&gt;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#89DDFF;">## </span><span style="color:#FFCB6B;">Results</span></span>
<span class="line"></span>
<span class="line"><span style="color:#89DDFF;">### </span><span style="color:#FFCB6B;">Site Data</span></span>
<span class="line"><span style="color:#A6ACCD;">&lt;pre&gt;{{ site }}&lt;/pre&gt;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#89DDFF;">### </span><span style="color:#FFCB6B;">Theme Data</span></span>
<span class="line"><span style="color:#A6ACCD;">&lt;pre&gt;{{ theme }}&lt;/pre&gt;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#89DDFF;">### </span><span style="color:#FFCB6B;">Page Data</span></span>
<span class="line"><span style="color:#A6ACCD;">&lt;pre&gt;{{ page }}&lt;/pre&gt;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#89DDFF;">### </span><span style="color:#FFCB6B;">Page Frontmatter</span></span>
<span class="line"><span style="color:#A6ACCD;">&lt;pre&gt;{{ frontmatter }}&lt;/pre&gt;</span></span>
<span class="line"></span></code></pre></div><h2 id="results" tabindex="-1">Results <a class="header-anchor" href="#results" aria-label="Permalink to &quot;Results&quot;">​</a></h2><h3 id="site-data" tabindex="-1">Site Data <a class="header-anchor" href="#site-data" aria-label="Permalink to &quot;Site Data&quot;">​</a></h3>`,6),m=a("h3",{id:"theme-data",tabindex:"-1"},[e("Theme Data "),a("a",{class:"header-anchor",href:"#theme-data","aria-label":'Permalink to "Theme Data"'},"​")],-1),u=a("h3",{id:"page-data",tabindex:"-1"},[e("Page Data "),a("a",{class:"header-anchor",href:"#page-data","aria-label":'Permalink to "Page Data"'},"​")],-1),_=a("h3",{id:"page-frontmatter",tabindex:"-1"},[e("Page Frontmatter "),a("a",{class:"header-anchor",href:"#page-frontmatter","aria-label":'Permalink to "Page Frontmatter"'},"​")],-1),g=a("h2",{id:"more",tabindex:"-1"},[e("More "),a("a",{class:"header-anchor",href:"#more","aria-label":'Permalink to "More"'},"​")],-1),D=a("p",null,[e("Check out the documentation for the "),a("a",{href:"https://vitepress.vuejs.org/reference/runtime-api#usedata",target:"_blank",rel:"noreferrer"},"full list of runtime APIs"),e(".")],-1),b=JSON.parse('{"title":"Runtime API Examples","description":"","frontmatter":{"outline":"deep"},"headers":[],"relativePath":"api-examples.md"}'),f={name:"api-examples.md"},y=Object.assign(f,{setup(C){const{site:n,theme:l,page:p,frontmatter:r}=o();return(A,F)=>(i(),c("div",null,[h,a("pre",null,s(t(n)),1),m,a("pre",null,s(t(l)),1),u,a("pre",null,s(t(p)),1),_,a("pre",null,s(t(r)),1),g,D]))}});export{b as __pageData,y as default};
