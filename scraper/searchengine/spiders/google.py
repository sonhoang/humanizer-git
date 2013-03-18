#!/usr/bin/env python
# encoding: utf-8

from scrapy.selector import HtmlXPathSelector
from scrapy.contrib.linkextractors.sgml import SgmlLinkExtractor
from scrapy.spider import BaseSpider
import urllib

from searchengine.items import SearchengineItem

QUERIES = ['entertainment',
           'free movies',
           'quán bia ngon rẻ',
           'bia hơi hà nội']
#QUERIES = ['entertainment']

def ConvertQueryToUrl(query):
    return 'http://www.google.com/search?q=' + urllib.quote_plus(query) + '&hl=vi&gl=vn'


class GoogleSpider(BaseSpider):
    name = 'google'
    allowed_domains = ['google.com']
    start_urls = []

    def __init__(self):
        GoogleSpider.start_urls = map(ConvertQueryToUrl, QUERIES)

    def parse(self, response):
        hxs = HtmlXPathSelector(response)
        i = SearchengineItem()
        center_column = hxs.select("//div[@id='ires']") 
        if not center_column:
            return
        for result in center_column.select("//li[@class='g']"):
            title = result.select(".//h3[@class='r']/a")
            if title:
                i['title'] = title.extract()[0]
                i['url'] = title.select("@href").extract()[0]
            snippet = result.select(".//span[@class='st']")
            if snippet:
                i['snippet'] = snippet.extract()[0]
        print i
        #import pdb;pdb.set_trace()
        return i
