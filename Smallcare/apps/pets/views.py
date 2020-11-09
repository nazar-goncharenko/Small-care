from django.http import response, HttpResponse


def index(request):
    return HttpResponse("Hello, my sweaty Pets!")


def test(request):
    return HttpResponse("Test Page")
